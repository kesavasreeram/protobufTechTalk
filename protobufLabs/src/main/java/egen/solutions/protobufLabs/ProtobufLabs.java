package egen.solutions.protobufLabs;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import egen.solutions.protocol.buffers.HackerStudentOuterClass.HackerStudent;
import egen.solutions.protocol.buffers.StudentOuterClass.Student;
import egen.solutions.protocol.buffers.StudentOuterClass.Student.Book;
import egen.solutions.protocol.buffers.StudentOuterClass.Student.CheckOut;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Hello world!
 *
 */
public class ProtobufLabs {
	public static final String BREAKER = "__________________________________________________________";
	
	public static void main(String[] args){
		System.out.println("Welcome to protoocol buffers");
	}

	public static void main1(String[] args) {

		// lets build a student object
		Student mo3pheus = Student.newBuilder().setName("Morpheus").setAddress("Operating Deck Nebuchadnezzar").build();
		System.out.println(mo3pheus.toString());
		System.out.println(BREAKER);

		// lets define a books owned by Sanket
		Book kaneAndAbel = Book.newBuilder().setBookName("Kane And Abel").setAuthor("Jeffrey Archer").build();
		Book codeComplete = Book.newBuilder().setBookName("Code complete").setAuthor("Steve McConnel").build();
		Book[] booksOwned = { kaneAndAbel, codeComplete };

		// morpheus starts to learn
		Student mo3pheus1 = Student.newBuilder().setName("Morpheus").setAddress("Operating Deck Nebuchadnezzar")
				.addAllBooksOwned(Arrays.asList(booksOwned)).build();
		System.out.println(mo3pheus1.toString());
		System.out.println(BREAKER);

		// adding checkouts
		CheckOut book1Checkout = CheckOut.newBuilder().setStudentName("Morpheus").setBookName("Kane and Abel")
				.setDueDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)).build();
		CheckOut book2Checkout = CheckOut.newBuilder().setStudentName("Morpheus").setBookName("codeComplete")
				.setDueDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(4)).build();
		CheckOut[] booksCheckedOut = { book1Checkout, book2Checkout };

		// with maps
		Student mo3pheus2 = Student.newBuilder().setName("Morpheus").setAddress("Operating Deck Nebuchadnezzar")
				.putBooksDue("Kane And Abel", System.currentTimeMillis())
				.putBooksDue("Hack the Matrix", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2)).build();
		System.out.println(mo3pheus2.toString());
		System.out.println(BREAKER);

		System.out.println("Lets look at a Json representation.");
		String secretsOfMorpheus = getJSonFromProject(codeComplete);
		System.out.println(secretsOfMorpheus);

		System.out.println(BREAKER);
		System.out.println("Lets get it back from the string");
		Book bookOfSecrets = getProjectFromJSONString(secretsOfMorpheus);
		System.out.println(bookOfSecrets.toString());

		// parsing
		System.out.println(BREAKER);
		System.out.println("Parsing back and forth");
		byte[] morphByteArr1 = mo3pheus2.toByteArray();

		try {
			Student morph34 = Student.parseFrom(morphByteArr1);
			System.out.println(morph34.toString());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// parsing
		System.out.println(BREAKER);
		System.out.println("But protobufs aren't secure");
		byte[] morphByteArr12 = mo3pheus2.toByteArray();

		try {
			HackerStudent morph34 = HackerStudent.parseFrom(morphByteArr12);
			System.out.println(morph34.toString());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Book getProjectFromJSONString(String jsonString) {
		Book localProject = null;
		try {
			Gson gson = new Gson();
			localProject = (Book) gson.fromJson(jsonString, Book.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localProject;
	}

	public static String getJSonFromProject(Book projObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(projObject);
	}
}
