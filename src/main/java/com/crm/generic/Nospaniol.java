package com.crm.generic;

import com.crm.model.Blacklist;
import com.crm.service.AuthorityService;
import com.crm.service.BlacklistService;
import com.crm.service.NationalityService;
import com.crm.model.Authority;
import com.crm.model.EmailToken;
import com.crm.model.PhoneToken;
import com.crm.model.User;
import com.crm.service.AccountService;
import com.crm.service.AgencyService;
import com.crm.service.ApplicationService;
import com.crm.service.AxleService;
import com.crm.service.CitationDisputeService;
import com.crm.service.CitationService;
import com.crm.service.CitationTypeService;
import com.crm.service.CompanyService;
import com.crm.service.CountryService;
import com.crm.service.ClientProfileService;
import com.crm.service.DepartmentAccountService;
import com.crm.service.DepartmentSavingService;
import com.crm.service.DepartmentService;
import com.crm.service.EmailTokenService;
import com.crm.service.FeeTypeService;
import com.crm.service.InvoiceService;
import com.crm.service.OrderAssetFileService;
import com.crm.service.OrderShippingFileService;
import com.crm.service.PhoneTokenService;
import com.crm.service.RegionService;
import com.crm.service.ReportService;
import com.crm.service.SavingService;
import com.crm.service.SequenceGeneratorService;
import com.crm.service.SessionService;
import com.crm.service.SpendingService;
import com.crm.service.StateService;
import com.crm.service.StoreLocationService;
import com.crm.service.SubDepartmentService;
import com.crm.service.TagTypeService;
import com.crm.service.TransactionDisputeService;
import com.crm.service.TransactionFileService;
import com.crm.service.TransactionService;
import com.crm.service.TransponderOrderService;
import com.crm.service.UserOtpService;
import com.crm.service.UserService;
import com.crm.service.VehicleFileService;
import com.crm.service.VehicleService;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import java.io.File;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import org.jasypt.util.text.AES256TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.crm.service.FullfillmentService;

@Component
public class Nospaniol {

    public static final Logger LOG = LoggerFactory.getLogger(Nospaniol.class);

    //@Lazy
    @Autowired
    public ApplicationService applicationService;
    //@Lazy
    @Autowired
    public VehicleFileService vehicleFileService;
    @Autowired
    public OrderAssetFileService orderAssetFileService;
    @Autowired
    public OrderShippingFileService orderShippingFileService;
    @Autowired
    public TransponderOrderService transponderOrderService;
    //@Lazy
    @Autowired
    public CitationDisputeService citationDisputeService;
    //@Lazy
    @Autowired
    public TransactionDisputeService transactionDisputeService;
     @Autowired
    public FullfillmentService fullfillmentService;
    //@Lazy
    @Autowired
    public SequenceGeneratorService seqGeneratorService;
    //@Lazy
    @Autowired
    public ReportService reportService;
    //@Lazy
    @Autowired
    public InvoiceService invoiceService;
    //@Lazy
    @Autowired
    public FeeTypeService feeTypeService;
    //@Lazy
    @Autowired
    public CitationService citationService;
    //@Lazy
    @Autowired
    public AgencyService agencyService;
    //@Lazy
    @Autowired
    public CountryService countryService;
    //@Lazy
    @Autowired
    public StateService stateService;
    //@Lazy
    @Autowired
    public CitationTypeService citationTypeService;
    //@Lazy
    @Autowired
    public DepartmentService departmentService;
    //@Lazy
    @Autowired
    public RegionService regionService;
    //@Lazy
    @Autowired
    public UserService userService;
    //@Lazy
    @Autowired
    public ClientProfileService clientProfileService;
    @Autowired
    public SubDepartmentService subDepartmentService;
    //@Lazy
    @Autowired
    public AuthorityService authorityService;
    //@Lazy
    @Autowired
    public SessionService sessionService;
    //@Lazy
    @Autowired
    public NationalityService nationalityService;
    //@Lazy
    @Autowired
    public BlacklistService blacklistService;
    //@Lazy
    @Autowired
    public PhoneTokenService phoneTokenService;
    //@Lazy
    @Autowired
    public EmailTokenService emailTokenService;
    //@Lazy
    @Autowired
    public UserOtpService otpService;
    //@Lazy
    @Autowired
    public AxleService axleService;
    //@Lazy
    @Autowired
    public TagTypeService tagTypeService;
    //@Lazy
    @Autowired
    public StoreLocationService storeLocationService;
    //@Lazy
    @Autowired
    public VehicleService vehicleService;
    //@Lazy
    @Autowired
    public CompanyService companyService;
    //@Lazy
    @Autowired
    public TransactionService transactionService;
    //@Lazy
    @Autowired
    public TransactionFileService transactionFileService;
    //@Lazy
    @Autowired
    public AccountService accountService;
    //@Lazy
    @Autowired
    public SpendingService spendingService;
    //@Lazy
    @Autowired
    public DepartmentAccountService departmentAccountService;
    //@Lazy
    @Autowired
    public SavingService savingService;
    //@Lazy
    @Autowired
    public DepartmentSavingService departmentSavingService;
    //@Lazy
    @Autowired
    public MongoTemplate mongoTemplate;

    public String institutionName() {
        return "Innovative Toll Solutions";
    }

    public void getDepartments(ModelAndView mv, HttpSession session) {
        // mv.addObject("departments", (List<Department>) session.getAttribute("departments"));
    }

    public String institutionMotto() {
        return "Innovative Toll Solutions";
    }

    public Date getCurrentDate() {
        try {
            String patterns = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
            Date today = simpleDateFormat.parse(String.valueOf(LocalDate.now()));
            return today;
        } catch (ParseException ex) {
            LOG.error(ex.toString());
            return null;
        }
    }

    public Date getConvertedDate(HttpSession session, String date) {
        Date today = null;
        String date_type = (String) session.getAttribute("ns_mail_address");
        if (date_type.equals("ddMMyyyy")) {

            if (isValidFormat("dd/MM/yyyy hh:mm:ss", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss");
                    String pattern = "dd/MM/yyyy hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd/MM/yyyy hh:mm:ss a", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss a");
                    String pattern = "dd/MM/yyyy hh:mm:ss a";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd/MM/yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss");
                    String pattern = "dd/MM/yyyy hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd-MM-yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd-MM-yyyy hh:mm:ss");
                    String pattern = "dd-MM-yyyy hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }
            if (isValidFormat("yyyy-DD-mm", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT yyyy-DD-mm hh:mm:ss");
                    String pattern = "yyyy-DD-mm hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

        } else {

            if (isValidFormat("MM/dd/yyyy hh:mm:ss", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss");
                    String pattern = "MM/dd/yyyy hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

            if (isValidFormat("MM/dd/yyyy hh:mm:ss a", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss a");
                    String pattern = "MM/dd/yyyy hh:mm:ss a";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

            if (isValidFormat("MM/dd/yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT MM/dd/yyyy hh:mm:ss");
                    String pattern = "MM/dd/yyyy hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("yyyy-MM-dd", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT yyyy-MM-dd hh:mm:ss");
                    String pattern = "yyyy-MM-dd hh:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

        }

        if (today != null) {
            LOG.info("Converted Date : " + today.toString());
        } else {
            LOG.info("Converted Date Failed");
        }
        return today;
    }

    public Date getConvertedDay(HttpSession session, String date) {
        Date today = null;
        String date_type = (String) session.getAttribute("ns_mail_address");
        if (date_type.equals("ddMMyyyy")) {

            if (isValidFormat("dd/MM/yyyy hh:mm:ss", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss");
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd/MM/yyyy hh:mm:ss a", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss a");
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd/MM/yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd/MM/yyyy hh:mm:ss");
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("dd-MM-yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT dd-MM-yyyy hh:mm:ss");
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("yyyy-mm-dd", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT yyyy-dd-mm");
                    String pattern = "yyyy-mm-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }
            if (isValidFormat("yyyy-MM-dd", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT yyyy-MM-dd");
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }
        } else {

            if (isValidFormat("MM/dd/yyyy hh:mm:ss", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT MM/dd/yyyy hh:mm:ss");
                    String pattern = "MM/dd/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

            if (isValidFormat("MM/dd/yyyy hh:mm:ss a", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT MM/dd/yyyy hh:mm:ss a");
                    String pattern = "MM/dd/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }

            }

            if (isValidFormat("MM/dd/yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT MM/dd/yyyy");
                    String pattern = "MM/dd/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }

            if (isValidFormat("mm-dd-yyyy", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT mm-dd-yyyy");
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }
            if (isValidFormat("yyyy-dd-mm", date, Locale.ENGLISH)) {
                try {
                    LOG.info("FORMAT yyyy-dd-mm");
                    String pattern = "yyyy-dd-mm";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
                    today = simpleDateFormat.parse(date);
                    return today;
                } catch (ParseException ex) {
                    LOG.error(ex.toString());
                    return null;
                }
            }
        }

        if (today != null) {
            LOG.info("Converted Date : " + today.toString());
        } else {
            LOG.info("Converted Date Failed");
        }
        return today;
    }

    public Date getConvertedDate(String date) {
        Date today = null;
        LocalDate todate = LocalDate.parse(date);
        today = convertToDateViaInstant(todate);
        today.setHours(00);
        today.setMinutes(00);
        today.setSeconds(00);
        return today;
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public boolean isValidFormat(String format, String strDate, Locale locale) {
        /* Check if date is 'null' */
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat(format);
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println(strDate + " is valid date format");
            } catch (ParseException e) {
                System.out.println(strDate + " is Invalid Date format");
                return false;
            }
            return true;
        }
    }

    public boolean iValidFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);
        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }

        return false;
    }

    public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public String getCurrentTimestamp() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        return timeStamp;
    }

    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

//    public boolean isNumeric(String strNum) {
//        if (strNum == null) {
//            return false;
//        }
//        try {
//            double d = Double.parseDouble(strNum);
//            return true;
//        } catch (NumberFormatException nfe) {
//            return false;
//        }
//    }
    public boolean sendText(String to, String message) {
        return true;
    }

    public List<String> getUsStates() {
        List<String> states = new ArrayList<>();
        states.add("Select state first");
        states.add("AL");
        states.add("MT");
        states.add("AK");
        states.add("NE");
        states.add("AZ");
        states.add("NV");
        states.add("AR");
        states.add("NH");
        states.add("CA");
        states.add("NJ");
        states.add("CO");
        states.add("NM");
        states.add("CT");
        states.add("NY");
        states.add("DE");
        states.add("NC");
        states.add("FL");
        states.add("ND");
        states.add("GA");
        states.add("OH");
        states.add("HI");
        states.add("OK");
        states.add("ID");
        states.add("OR");
        states.add("IL");
        states.add("PA");
        states.add("IN");
        states.add("RI");
        states.add("IA");
        states.add("SC");
        states.add("KS");
        states.add("SD");
        states.add("KY");
        states.add("TN");
        states.add("LA");
        states.add("TX");
        states.add("ME");
        states.add("UT");
        states.add("MD");
        states.add("VT");
        states.add("MA");
        states.add("VA");
        states.add("MI");
        states.add("WA");
        states.add("MN");
        states.add("WV");
        states.add("MS");
        states.add("WI");
        states.add("MO");
        states.add("WY");
        return states;
    }

    public List<String> countries() {
        List<String> countries = new ArrayList<>();
        countries.add("Afghanistan");
        countries.add("Albania");
        countries.add("Algeria");
        countries.add("Andorra");
        countries.add("Angola");
        countries.add("Anguilla");
        countries.add("Antigua & Barbuda");
        countries.add("Argentina");
        countries.add("Armenia");
        countries.add("Aruba");
        countries.add("Australia");
        countries.add("Austria");
        countries.add("Azerbaijan");
        countries.add("Bahamas");
        countries.add("Bahrain");
        countries.add("Bangladesh");
        countries.add("Barbados");
        countries.add("Belarus");
        countries.add("Belgium");
        countries.add("Belize");
        countries.add("Benin");
        countries.add("Bermuda");
        countries.add("Bhutan");
        countries.add("Bolivia");
        countries.add("Bosnia & Herzegovina");
        countries.add("Botswana");
        countries.add("Brazil");
        countries.add("British Virgin Islands");
        countries.add("Brunei");
        countries.add("Bulgaria");
        countries.add("Burkina Faso");
        countries.add("Burundi");
        countries.add("Cambodia");
        countries.add("Cameroon");
        countries.add("Cape Verde");
        countries.add("Cayman Islands");
        countries.add("Chad");
        countries.add("Chile");
        countries.add("China");
        countries.add("Colombia");
        countries.add("Congo");
        countries.add("Cook Islands");
        countries.add("Costa Rica");
        countries.add("Cote D Ivoire");
        countries.add("Croatia");
        countries.add("Cruise Ship");
        countries.add("Cuba");
        countries.add("Cyprus");
        countries.add("Czech Republic");
        countries.add("Denmark");
        countries.add("Djibouti");
        countries.add("Dominica");
        countries.add("Dominican Republic");
        countries.add("Ecuador");
        countries.add("Egypt");
        countries.add("El Salvador");
        countries.add("Equatorial Guinea");
        countries.add("Estonia");
        countries.add("Ethiopia");
        countries.add("Falkland Islands");
        countries.add("Faroe Islands");
        countries.add("Fiji");
        countries.add("Finland");
        countries.add("France");
        countries.add("French Polynesia");
        countries.add("French West Indies");
        countries.add("Gabon");
        countries.add("Gambia");
        countries.add("Georgia");
        countries.add("Germany");
        countries.add("Ghana");
        countries.add("Gibraltar");
        countries.add("Greece");
        countries.add("Greenland");
        countries.add("Grenada");
        countries.add("Guam");
        countries.add("Guatemala");
        countries.add("Guernsey");
        countries.add("Guinea");
        countries.add("Guinea Bissau");
        countries.add("Guyana");
        countries.add("Haiti");
        countries.add("Honduras");
        countries.add("Hong Kong");
        countries.add("Hungary");
        countries.add("Iceland");
        countries.add("India");
        countries.add("Indonesia");
        countries.add("Iran");
        countries.add("Iraq");
        countries.add("Ireland");
        countries.add("Isle of Man");
        countries.add("Israel");
        countries.add("Italy");
        countries.add("Jamaica");
        countries.add("Japan");
        countries.add("Jersey");
        countries.add("Jordan");
        countries.add("Kazakhstan");
        countries.add("Kenya");
        countries.add("Kuwait");
        countries.add("Kyrgyz Republic");
        countries.add("Laos");
        countries.add("Latvia");
        countries.add("Lebanon");
        countries.add("Lesotho");
        countries.add("Liberia");
        countries.add("Libya");
        countries.add("Liechtenstein");
        countries.add("Lithuania");
        countries.add("Luxembourg");
        countries.add("Macau");
        countries.add("Macedonia");
        countries.add("Madagascar");
        countries.add("Malawi");
        countries.add("Malaysia");
        countries.add("Maldives");
        countries.add("Mali");
        countries.add("Malta");
        countries.add("Mauritania");
        countries.add("Mauritius");
        countries.add("Mexico");
        countries.add("Moldova");
        countries.add("Monaco");
        countries.add("Mongolia");
        countries.add("Montenegro");
        countries.add("Montserrat");
        countries.add("Morocco");
        countries.add("Mozambique");
        countries.add("Namibia");
        countries.add("Nepal");
        countries.add("Netherlands");
        countries.add("Netherlands Antilles");
        countries.add("New Caledonia");
        countries.add("New Zealand");
        countries.add("Nicaragua");
        countries.add("Niger");
        countries.add("Nigeria");
        countries.add("Norway");
        countries.add("Oman");
        countries.add("Pakistan");
        countries.add("Palestine");
        countries.add("Panama");
        countries.add("Papua New Guinea");
        countries.add("Paraguay");
        countries.add("Peru");
        countries.add("Philippines");
        countries.add("Poland");
        countries.add("Portugal");
        countries.add("Puerto Rico");
        countries.add("Qatar");
        countries.add("Reunion");
        countries.add("Romania");
        countries.add("Russia");
        countries.add("Rwanda");
        countries.add("Saint Pierre & Miquelon");
        countries.add("Samoa");
        countries.add("San Marino");
        countries.add("Satellite");
        countries.add("Saudi Arabia");
        countries.add("Senegal");
        countries.add("Serbia");
        countries.add("Seychelles");
        countries.add("Sierra Leone");
        countries.add("Singapore");
        countries.add("Slovakia");
        countries.add("Slovenia");
        countries.add("South Africa");
        countries.add("South Korea");
        countries.add("Spain");
        countries.add("Sri Lanka");
        countries.add("St Kitts & Nevis");
        countries.add("St Lucia");
        countries.add("St Vincent");
        countries.add("St. Lucia");
        countries.add("Sudan");
        countries.add("Suriname");
        countries.add("Swaziland");
        countries.add("Sweden");
        countries.add("Switzerland");
        countries.add("Syria");
        countries.add("Taiwan");
        countries.add("Tajikistan");
        countries.add("Tanzania");
        countries.add("Thailand");
        countries.add("Timor L'Este");
        countries.add("Togo");
        countries.add("Tonga");
        countries.add("Trinidad & Tobago");
        countries.add("Tunisia");
        countries.add("Turkey");
        countries.add("Turkmenistan");
        countries.add("Turks & Caicos");
        countries.add("Uganda");
        countries.add("Ukraine");
        countries.add("United Arab Emirates");
        countries.add("United Kingdom");
        countries.add("Uruguay");
        countries.add("Uzbekistan");
        countries.add("Venezuela");
        countries.add("Vietnam");
        countries.add("Virgin Islands (US)");
        countries.add("Yemen");
        countries.add("Zambia");
        countries.add("Zimbabwe");
        return countries;
    }

    public List<String> counties() {
        List<String> counties = new ArrayList<>();
        counties.add("Mombasa");
        counties.add("Isiolo");
        counties.add("Murang'a");
        counties.add("Laikipia");
        counties.add("Siaya");
        counties.add("Kwale");
        counties.add("Meru");
        counties.add("Kiambu");
        counties.add("Nakuru");
        counties.add("Kisumu");
        counties.add("Kilifi");
        counties.add("Tharaka-Nithi");
        counties.add("Turkana");
        counties.add("Narok");
        counties.add("Homa Bay");
        counties.add("Tana River");
        counties.add("Embu");
        counties.add("West Pokot");
        counties.add("Kajiado");
        counties.add("Migori");
        counties.add("Lamu");
        counties.add("Kitui");
        counties.add("Samburu");
        counties.add("Kericho");
        counties.add("Kisii");
        counties.add("Taita-Taveta");
        counties.add("Machakos");
        counties.add("Trans Nzoia");
        counties.add("Bomet");
        counties.add("Nyamira");
        counties.add("Garissa");
        counties.add("Makueni");
        counties.add("Uasin Gishu");
        counties.add("Kakamega");
        counties.add("Nairobi");
        counties.add("Wajir");
        counties.add("Nyandarua");
        counties.add("Elgeyo-Marakwet");
        counties.add("Vihiga");
        counties.add("Mandera");
        counties.add("Nyeri");
        counties.add("Nandi");
        counties.add("Bungoma");
        counties.add("Marsabit");
        counties.add("Kirinyaga");
        counties.add("Baringo");
        counties.add("Busia");
        return counties;
    }

    public void fileRename(String folder) {
        File file = new File(folder);
        System.out.println("Reading this " + file.toString());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            List<File> filelist = Arrays.asList(files);
            filelist.forEach(f -> {
                if (!f.isDirectory() && f.getName().startsWith("Old")) {
                    System.out.println(f.getAbsolutePath());
                    String newName = f.getAbsolutePath().replace("Old", "New");
                    boolean isRenamed = f.renameTo(new File(newName));
                    if (isRenamed) {
                        System.out.println(String.format("Renamed this file %s to  %s", f.getName(), newName));
                    } else {
                        System.out.println(String.format("%s file is not renamed to %s", f.getName(), newName));
                    }
                }
            });

        }
    }

    public void getUser() {

        Authority useAut = authorityService.findByRole("STAFF");
        if (useAut == null) {
            Authority useAuth = new Authority();
            useAuth.setAuthorityId(seqGeneratorService.generateSequence(Authority.SEQUENCE_NAME));
            useAuth.setRole("STAFF");
            useAuth.setStatus(true);
            authorityService.save(useAuth);
        }

        Authority clientAut = authorityService.findByRole("CLIENT");
        if (clientAut == null) {
            Authority clientAuth = new Authority();
            clientAuth.setAuthorityId(seqGeneratorService.generateSequence(Authority.SEQUENCE_NAME));
            clientAuth.setRole("CLIENT");
            clientAuth.setStatus(true);
            authorityService.save(clientAuth);
        }

        Authority depAut = authorityService.findByRole("CLIENT_DEPARTMENT");
        if (depAut == null) {
            Authority depAuth = new Authority();
            depAuth.setAuthorityId(seqGeneratorService.generateSequence(Authority.SEQUENCE_NAME));
            depAuth.setRole("CLIENT_DEPARTMENT");
            depAuth.setStatus(true);
            authorityService.save(depAuth);
        }

        Authority aut = authorityService.findByRole("ADMINISTRATOR");
        if (aut == null) {
            Authority auth = new Authority();
            auth.setAuthorityId(seqGeneratorService.generateSequence(Authority.SEQUENCE_NAME));
            auth.setRole("ADMINISTRATOR");
            auth.setStatus(true);
            aut = authorityService.save(auth);
        }
        if (aut == null) {
            return;
        } else {
            User user = userService.findByEmailToken(this.emailTokenService.findByEmailAddress("admin@admin.com"));
            if (user == null) {
                user = new User();
                user.setUserId(seqGeneratorService.generateSequence(User.SEQUENCE_NAME));
                String otp = this.generateOtp();
                String eotp = this.generateOtp();
                PhoneToken token = new PhoneToken();
                token.setPhoneNumber("0701211265");
                token.setPhoneOtp(this.getHashed(otp));
                token.setStatus(false);
                token.setPhoneTokenId(seqGeneratorService.generateSequence(PhoneToken.SEQUENCE_NAME));
                PhoneToken phone = this.phoneTokenService.save(token);
                EmailToken etoken = new EmailToken();
                etoken.setEmailAddress("admin@admin.com".toLowerCase());
                etoken.setEmailOtp(this.getHashed(eotp));
                etoken.setStatus(false);
                etoken.setEmailTokenId(seqGeneratorService.generateSequence(EmailToken.SEQUENCE_NAME));
                EmailToken email = this.emailTokenService.save(etoken);
                user.setStatus(true);
                user.setPassword(this.getHashed("password"));
                user.setAuthority(aut);
                user.setPhoneToken(phone);
                user.setEmailToken(email);
                user.setFirstName("Innovative");
                user.setLastName("Toll");
                User newUser = userService.save(user);
            } else {
                return;
            }
        }
    }

    public boolean sendMail(String to, String subject, String body) {
        String password = "Patricia@2O";
        String from = "nospaniol@gmail.com";
        LOG.info("Sender Account : " + from);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.debug", "true");
        javax.mail.Session sess = javax.mail.Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(sess);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("noreply@gudest.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            LOG.info(mex.toString());
            return false;
        }

    }

    public String getLoggedIn(javax.servlet.http.HttpSession session) {
        return (String) session.getAttribute("ns_user");
    }

    public User getLoggedUser(javax.servlet.http.HttpSession session) {
        EmailToken etoken = this.emailTokenService.findByEmailAddress((String) session.getAttribute("ns_user"));
        User checkUser = userService.findByEmailToken(etoken);
        return checkUser;
    }

    public String getHashed(String token) {
        HashFunction hashFunction = Hashing.sha1();
        com.google.common.hash.HashCode hashCode = hashFunction.hashUnencodedChars(token);
        return hashCode.toString().toUpperCase();
    }

    public String getAccessPermition() {
        return "";
    }

    public boolean checkBlacklist(String nid) {
        Blacklist blacklist = blacklistService.findByEmail(nid);
        return (blacklist != null);
    }

    public void loggerSoln(Exception ex) {
        LOG.info(ex.toString());
        ex.printStackTrace();
    }

    public void loggerSoln(String ex) {
        LOG.info("\n***********************************\n");
        LOG.info(ex);
        LOG.info("\n***********************************\n");
    }

    public void setInstitution(Model institution) {
        institution.addAttribute("institution", institutionName());
        institution.addAttribute("motto", institutionMotto());
    }

    public void setInstitution(ModelAndView institution) {
        institution.addObject("institution", institutionName());
        institution.addObject("motto", institutionMotto());
    }

    public boolean checkLogin(javax.servlet.http.HttpSession session) {
        EmailToken etoken = this.emailTokenService.findByEmailAddress((String) session.getAttribute("ns_user"));
        User checkUser = userService.findByEmailToken(etoken);
        return checkUser != null;
    }

    public Double getTwoDecimal(Double PI) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(PI));
    }

    public Double getThreeDecimal(Double PI) {
        DecimalFormat df = new DecimalFormat("###.###");
        return Double.valueOf(df.format(PI));
    }

    public String generateOtp() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10000) + 99;
        String convid = String.valueOf(randomNum);
        return convid;
    }

    public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public String getFileExtension(MultipartFile file) {
        return Files.getFileExtension(file.getOriginalFilename());
    }

    public static String getLastDayOfMonth(int year, int month) {
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(year + "-" + (month < 10 ? ("0" + month) : month) + "-01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            Date lastDayOfMonth = calendar.getTime();
            return sdf.format(lastDayOfMonth);
        } catch (ParseException ex) {
            LOG.info(ex.toString());
            return String.valueOf(LocalDate.now());
        }
    }

    public String encryptWord(String word) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("nospaniolnoah");
        String encryptedPass = textEncryptor.encrypt(word);
        return encryptedPass;
    }

    public String decryptWord(String word) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("nospaniolnoah");
        String encryptedPass = textEncryptor.decrypt(word);
        return encryptedPass;
    }

    private SecretKeySpec secretKey;
    private byte[] key;

    public void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
