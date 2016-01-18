package appian.ci.querynamebyuuid;

import appian.ci.core.EndpointResolver;
import appian.ci.core.UuidUtil;
import common.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Command {

    private static final Logger logger = Logger.getLogger(Command.class.getName());
    private static final String API_PATH = "webapi/getContent";
    private final UuidUtil uuidUtil;
    private final Charset encoding = Charset.forName("UTF-16");
            
    public Command(UuidUtil uuidUtil)
    {
        this.uuidUtil = uuidUtil;
        
    }
    
    public Iterable<Result> execute(Iterable<String> uuids, URL server, String username, String password) throws MalformedURLException {
        
        List<Result> results = new LinkedList<>();

        for (String uuid : uuids) {
            
            if (uuidUtil.isProcessModelFolder(uuid) ||
                uuidUtil.isUserGroup(uuid))
            {
                continue;
            }
            
            results.add(execute(uuid, server, username, password));
        }

        return results;
    }

    public Iterable<Result> executeWithFile(File uuidsFile, URL server, String username, String password) throws MalformedURLException {
        
        logger.info("Executing QueryNameByUuid url=" + server.toString());
        
        try {
            List<String> uuids = Files.readAllLines(uuidsFile.toPath(), encoding);
        
            return execute(uuids, server, username, password);
            

        } catch (IOException ex) {

            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    private Result execute(String uuid, URL server, String username, String password) throws MalformedURLException {
        
        URL endPoint = EndpointResolver.resolve(server, API_PATH);

        String uuidFixed = uuidUtil.fromString(uuid);
        
        HttpRequest request = HttpRequest
            .get(HttpRequest.append(endPoint.toString(), "uuid", uuid))
            .basic(username, password);

        logger.log(Level.INFO, "{2} GET {0}?uuid={1}", new Object[] { endPoint.toString(), uuidFixed, request.code() });

        if (request.code() == 200)
        {
            return new Result(uuid, uuidFixed, request.body());
        }
        else
        {
            throw new RuntimeException(request.code() + " " + request.message());
        }
    }

    public class Result
    {
        public final String sourceUuid;
        public final String fixedUuid;
        public final String response;

        /**
         *
         * @param sourceUuid
         * @param fixedUuid
         * @param response
         */
        public Result(String sourceUuid, String fixedUuid, String response)
        {
            this.sourceUuid = sourceUuid;
            this.fixedUuid = fixedUuid;
            this.response = response;
            
        }
    }
}
