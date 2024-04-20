package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    SocialMediaService socialMediaService;
    public SocialMediaController(){
        socialMediaService = new SocialMediaService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register",this::addNewUser);
        app.post("/login",this::logger);
        app.post("/messages",this::messageGenerator);
        app.get("/messages",this::messageFeed);
        app.get("/messages/{message_id}",this::messageRetrieve);
        app.delete("/messages/{message_id}",this::messageDelete);
        app.patch("/messages/{message_id}",this::messageUpdate);
        app.get("/accounts/{account_id}",this::UserFeed);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void addNewUser(Context ctx)  throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        List <Account> accounts = socialMediaService.getAllAccounts();
        for(Account accnt : accounts){
            if(accnt.getPassword() ==account.getPassword() && account.getUsername() == accnt.getUsername()){
                ctx.status(400);
            }
        }
        if(account.getUsername().length()>0 && account.getPassword().length()>4){
            Account addedAccount = socialMediaService.addUser(account);
            ctx.json(addedAccount, Account.class);
            ctx.status(200);
        }
        ctx.status(400);
    }
    private void logger(Context ctx)  throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account res = socialMediaService.accountVerify(account.getUsername(), account.getPassword());
        if( res != null){
            ctx.json(res, Account.class);
            ctx.status(200);
        }
        ctx.status(401);

    }

    private void messageGenerator(Context ctx)  throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        String txt = msg.getMessage_text();
        int user = msg.getPosted_by();
        Account a = socialMediaService.getUser(user);
        if(txt.length()==0 || txt.length()> 255 || a == null)
            ctx.status(400);
            
        Message newMessage = socialMediaService.addMessage(msg);
            ctx.json(newMessage);
            ctx.status(200);

    }

    private void messageFeed(Context ctx) throws JsonProcessingException{
        List<Message> messages = socialMediaService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);

    }

    private void messageRetrieve(Context ctx){
        int id =Integer.parseInt(ctx.pathParam("message_id"));
            Message message = socialMediaService.getMessage(id);
            if(message !=null)
                ctx.json(message);
            ctx.status(200);
    }
    
    private void messageDelete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = socialMediaService.deleteMessage(id);
            if(message != null)
                ctx.json(message);
            ctx.status(200);
    }

    private void messageUpdate(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = mapper.readValue(ctx.body(), Message.class);
        if( msg.getMessage_id() == 0 || msg.getMessage_text().length() == 0 || msg.getMessage_text().length()>255){
            ctx.status(400);
        }
        Message newMessage= socialMediaService.updateMessageById(id,msg);
        ctx.json(newMessage);
        ctx.status(200);

    }

    private void UserFeed(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = socialMediaService.getUserFeed(id);
        ctx.json(messages);
        ctx.status(200);

    }




}