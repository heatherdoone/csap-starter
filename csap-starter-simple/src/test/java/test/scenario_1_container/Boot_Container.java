package test.scenario_1_container;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.CsapStarterDemo;
import org.sample.HelloService;
import org.sample.LandingPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import a_setup.InitializeLogging;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsapStarterDemo.class)
@ActiveProfiles("junit")
public class Boot_Container {
	final static private Logger logger = LoggerFactory.getLogger( Boot_Container.class );

	@BeforeClass
	public static void setUpBeforeClass ()
			throws Exception {
		InitializeLogging.printTestHeader( logger.getName() );
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void load_context() {
		logger.info( InitializeLogging.TC_HEAD );

		assertThat( applicationContext.getBeanDefinitionCount() )
				.as( "Spring Bean count" )
				.isGreaterThan( 100 );

		assertThat( applicationContext.getBean( LandingPage.class ) )
				.as( "SpringRequests controller loaded" )
				.isNotNull();

		assertThat( applicationContext.getBean( HelloService.class ) )
				.as( "Demo_DataAccessObject  loaded" )
				.isNotNull();
		
		// Assert.assertFalse( true);

	}

	@Test
	public void testEncode() throws UnsupportedEncodingException {
		
		String companyName="Test Networks (test)" ;
		String companyNameEncoded = URLEncoder.encode( companyName, StandardCharsets.UTF_8.name() ) ;
		
		logger.info("{} encoded: {}", companyName, companyNameEncoded) ;
		
	}
	
	@Inject
	private StandardPBEStringEncryptor encryptor ;
	
	
	@Test
	public void testEncryption() {

		
		String testSample = "Simple encrypt test" ;
		String encSample = encryptor.encrypt( testSample) ;

		String message = "Encoding of  " + testSample + " is " + encSample ;
		logger.info(InitializeLogging.TC_HEAD +  message);
		
		assertThat(testSample).isNotEqualTo( encSample) ;

		assertThat(testSample).isEqualTo( encryptor.decrypt( encSample)) ;
		//assertTrue(  encryptor.decrypt( encSample).equals( testSample)  ) ; 
		
	}
	
}
