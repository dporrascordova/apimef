package mef.application.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {


  private final DocumentScheduler myComponent;

  public MyCommandLineRunner(DocumentScheduler myComponent) {
    this.myComponent = myComponent;
  }

  @Override
  public void run(String... args) throws Exception {
//    myComponent.ScheduletDocumentSGDD();
  }
}
