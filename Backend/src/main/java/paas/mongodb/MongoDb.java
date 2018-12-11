package paas.mongodb;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class MongoDb {
	
	private ApplicationContext ApplicationContext;
	
	private String DbHost;
	private Integer DbPort;
	private String Database;
	private String Username;
	private String Password;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ApplicationContext = applicationContext;
        
        this.DbHost = this.ApplicationContext.getEnvironment().getProperty("spring.data.mongodb.host");
        this.DbPort = Integer.parseInt(this.ApplicationContext.getEnvironment().getProperty("spring.data.mongodb.port"));
        this.Database = this.ApplicationContext.getEnvironment().getProperty("spring.data.mongodb.host");
        //if your config is enabled to ask username/password.. 
        this.Username = this.ApplicationContext.getEnvironment().getProperty("spring.data.mongodb.username");
        this.Password = this.ApplicationContext.getEnvironment().getProperty("spring.data.mongodb.password");
        
    }
	
	
	//ApplicationContext.getEnvironment().getProperty(<keyName>)
}
