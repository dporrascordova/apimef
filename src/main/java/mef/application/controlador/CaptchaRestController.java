package mef.application.controlador;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

@RestController
//@CrossOrigin("*")
public class CaptchaRestController {
	
    @RequestMapping(value="verify/captcha", method = RequestMethod.POST)
    public ResponseEntity<?> validateReCaptha(@RequestBody Map<String, String> captchaMap){
        if(!isCaptchaValid("private-key", captchaMap.get("captcha"))){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.noContent().build();
    }

    public static boolean isCaptchaValid(String secretKey, String response) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify?"
                    + "secret=" + secretKey
                    + "&response=" + response;
            InputStream res = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            res.close();

            JSONObject json = new JSONObject(jsonText);
            return json.getBoolean("success");
        } catch (Exception e) {
        	System.out.println("Capcha : " + e.getStackTrace());
            return false;
        }
    }

}
