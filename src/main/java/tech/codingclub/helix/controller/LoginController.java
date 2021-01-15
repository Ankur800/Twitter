package tech.codingclub.helix.controller;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.encryptionDecryption.Decryption;
import tech.codingclub.helix.encryptionDecryption.Encryption;
import tech.codingclub.helix.entity.LoginResponse;
import tech.codingclub.helix.entity.Member;
import tech.codingclub.helix.entity.SignupResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String adminLogin(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "adminlogin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public String userLogin(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "userlogin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public String welcome(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "welcomelogin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/welcome")
    public
    @ResponseBody
    LoginResponse handleLogin(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {

        //Decryption
        //************************************************************
        /*
        Condition encryptionCondition = tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.email);
        List<Member> userData = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, encryptionCondition, 1);
        int encryptionIdToDecrypt = 0;
        for(Member userDatum : userData){
            encryptionIdToDecrypt = userDatum.encryptionID;
        }

        Encryption encryption = new Encryption(member.password, encryptionIdToDecrypt);
        member.password = encryption.encrypt();
         */
        //*************************************************************

        Condition condition = tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.email).and(tech.codingclub.helix.tables.Member.MEMBER.PASSWORD.eq(member.password));

        List<Member> x = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, condition, 1);

        if(x != null && x.size() > 0){
            //condition met ! and user is entering right email and password

            Member memberTemp = x.get(0);
            memberTemp.role = "cm";
            ControllerUtils.setUserSession(request, memberTemp);
            return new LoginResponse(memberTemp.id, true, "Login Successful");
        } else {
            //Wrong combination
            return new LoginResponse(null, false, "Wrong combination!");
        }
    }
}