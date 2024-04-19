package Service;
import java.util.List;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import DAO.SocialMediaDAO;

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
    

}
