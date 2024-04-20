package Service;
import java.util.List;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import DAO.SocialMediaDAO;
import java.util.List;


public class SocialMediaService {
    public SocialMediaDAO socialMediaDAO;

    public SocialMediaService(){
        socialMediaDAO = new SocialMediaDAO(); 
    }
    public SocialMediaService(SocialMediaDAO socialMediaDAO){
        this.socialMediaDAO = socialMediaDAO; 
    }

    public List<Message> getAllMessages(){
        return socialMediaDAO.getAllMessages();
    }
    public Account addUser(Account accnt){
        return socialMediaDAO.newAccount(accnt);
    }
    public Message addMessage(Message msg){
        return socialMediaDAO.newMessage(msg);
    }
    public Account getUser(int id){
        return socialMediaDAO.getAccountById(id);
    }


    public List<Account> getAllAccounts(){
        return socialMediaDAO.getAllAccounts();
    }


}
