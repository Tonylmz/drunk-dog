package com.seu.drunkdog.controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
@RestController
@Service
@CrossOrigin(origins = "http://localhost:8080",maxAge = 36000)
public class TestController {
    @RequestMapping("/test")
    public void test() throws Exception{
        String pyPath = "main.py";
        String stateStr = "--user-id 16";
        String[] pyPath2 = {"python","E:\\Study\\vscodeProject\\pythonProject\\test.py"};
        String[] pyPath3 = {"python", "E:\\Study\\intern\\hmm.py","--text 我喜欢吴京的电影"};
        String[] args1 = new String[] {"cmd.exe","ssh root@118.31.14.231","Ai2022!!","python main.py --user-id 16"};
//        String[] args1 = new String[] {"ssh root@118.31.14.231","Ai2022!!","python", pyPath, stateStr};
        String arg2 = "ssh root@118.31.14.231";


        Process proc = Runtime.getRuntime().exec(pyPath3);

        BufferedReader in = new BufferedReader(new InputStreamReader( proc.getInputStream()));
        String actionStr = in.readLine();
        if (actionStr != null)
            System.out.println(actionStr);

        in.close();
        proc.waitFor();
        System.out.println("success");

    }

}
