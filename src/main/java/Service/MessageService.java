package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(message);
    }

    public List<Message> getMessagesByUser(int account_id) {
        return messageDAO.getMessageByUser(account_id);
    }
}
