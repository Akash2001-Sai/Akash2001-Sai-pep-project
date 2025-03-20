package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper objectMapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.objectMapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //Account endpoints
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);

        //Message endpoints
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUser(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account registeredAccount = accountService.registerUser(account);
            if (registeredAccount != null) {
                ctx.json(registeredAccount);
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void loginUser(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account loggedInAccount = accountService.loginUser(account);
            if (loggedInAccount != null) {
                ctx.json(loggedInAccount);
                ctx.status(200);
            } else {
                ctx.status(401);
            }
        } catch (Exception e) {
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx) {
        try {
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            Message createdMessage = messageService.createMessage(message);
            if (createdMessage != null) {
                ctx.json(createdMessage);
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessageById(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessageById(message_id);
        if (deleteMessage != null) {
            ctx.json(deleteMessage);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) {
        try {
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            message.setMessage_id(message_id);
            Message updateMessage = messageService.updateMessage(message);
            if (updateMessage != null) {
                ctx.json(updateMessage);
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void getMessagesByUser(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(account_id);
        ctx.json(messages);
        ctx.status(200);
    }
}

// creating a end