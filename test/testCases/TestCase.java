package testCases;

import java.util.Calendar;
import model.ContactPerson;
import model.Customer;
import model.Note;


public class TestCase
{
    public static class TestCustomer
    {
        public static final int CID = 9999;
        public static final String COMPANY = "testCompany";
        public static final String ADDRESS = "testAddress";
        public static final String ZIP = "testZip";
        public static final String CITY = "testCity";
        public static final String COUNTRY = "testCountry";
        public static final String CONTRACT_ID = "testContractId";
        public static final Calendar getContractDate()
        {
            Calendar c;
            c = Calendar.getInstance();
            c.set(2017, 1, 1, 1, 1, 1);
            c.set(Calendar.MILLISECOND, 1);
            return c;
        }
    }
    
    public static Customer getTestCustomer()
    {
        Customer c = new Customer();
        
        c.setCid(TestCustomer.CID);
        c.setCompany(TestCustomer.COMPANY);
        c.setAddress(TestCustomer.ADDRESS);
        c.setZip(TestCustomer.ZIP);
        c.setCity(TestCustomer.CITY);
        c.setCountry(TestCustomer.COUNTRY);
        c.setContractId(TestCustomer.CONTRACT_ID);
        c.setContractDate(TestCustomer.getContractDate());
        
        return c;
    }
    
    public static class TestContactPerson
    {
        public static final int ID = 9999;
        public static final int CID = 9999;
        public static final String FORENAME = "testForename";
        public static final String SURNAME = "testSurname";
        public static final Character GENDER = 'm';
        public static final String EMAIL = "test@test.com";
        public static final String PHONE = "123";
        public static final boolean MAIN_CONTACT = true;
    }
    
    public static ContactPerson getTestContactPerson()
    {
        ContactPerson cp = new ContactPerson();
        
        cp.setId(TestContactPerson.ID);
        cp.setCid(TestContactPerson.CID);
        cp.setForename(TestContactPerson.FORENAME);
        cp.setSurname(TestContactPerson.SURNAME);
        cp.setGender(TestContactPerson.GENDER);
        cp.setEmail(TestContactPerson.EMAIL);
        cp.setPhone(TestContactPerson.PHONE);
        cp.setMainContact(TestContactPerson.MAIN_CONTACT);
        
        return cp;
    }
    
    public static class TestNote
    {
        public static final int ID = 9999;
        public static final int CID = 9999;
        public static final String CREATED_BY = "testUser";
        public static final Calendar getEntryDate()
        {
            Calendar c;
            c = Calendar.getInstance();
            c.set(2017, 1, 1, 1, 1, 1);
            c.set(Calendar.MILLISECOND, 1);
            return c;
        }
        public static final String MEMO = "testMemo";
        public static final String CATEGORY = "testCategory";
        public static final String ATTACHMENT = "test.txt";
    }
    
    public static Note getTestNote()
    {
        Note n = new Note();
        
        n.setId(TestNote.ID);
        n.setCid(TestNote.CID);
        n.setCreatedBy(TestNote.CREATED_BY);
        n.setEntryDate(TestNote.getEntryDate());
        n.setMemo(TestNote.MEMO);
        n.setCategory(TestNote.CATEGORY);
        n.setAttachment(TestNote.ATTACHMENT);
        
        return n;
    }
}
