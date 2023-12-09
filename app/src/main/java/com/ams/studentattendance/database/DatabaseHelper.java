package com.ams.studentattendance.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ams.studentattendance.R;
import com.ams.studentattendance.bean.FacultyBean;
import com.ams.studentattendance.bean.StudentAttendanceBean;
import com.ams.studentattendance.bean.StudentBean;
import com.ams.studentattendance.bean.SubjectBean;
import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.bean.YearBean;
import com.ams.studentattendance.dto.MappingHelperBean;
import com.ams.studentattendance.dto.MarkAttendanceStudentBean;
import com.ams.studentattendance.dto.ViewAttendanceBean;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewFacultySubjectMappingBean;
import com.ams.studentattendance.dto.ViewStudentBean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

public class DatabaseHelper extends SQLiteOpenHelper {

    /* All Static variables */

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Attendance.db";
    public final static String DATABASE_PATH = "/data/data/com.ams.studentattendance/databases/";
    public final static String dbPath = DATABASE_PATH + DATABASE_NAME;

    public static List<String> gender = new ArrayList<>();
    public static List<String> facultyType = new ArrayList<>();
    public static List<String> userType = new ArrayList<>();
    public static List<String> division = new ArrayList<>();

    static {
        gender.add("Male");
        gender.add("Female");
        gender.add("Other");

        facultyType.add("Permanent");
        facultyType.add("Contract");

        userType.add("Admin");
        userType.add("Faculty");
        userType.add("Student");

        division.add("A");
        division.add("B");
        division.add("C");
    }

    // Table names and Columns and Create Queries
    private static final String MST_BRANCH = "mst_branch";
    private static final String KEY_BRANCH_ID = "branchId";
    private static final String COL_BRANCH_NAME = "branchName";
    private static final String createQueryBranch = "CREATE TABLE "+ MST_BRANCH +" (" +
            KEY_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_BRANCH_NAME + " TEXT)";
    private static final String dropQueryBranch = "DROP TABLE IF EXISTS " + MST_BRANCH;

    private static final String MST_YEAR = "mst_year";
    private static final String KEY_YEAR_ID = "yearId";
    private static final String KEY_YEAR = "year";
    private static final String FKEY_BRANCH_ID = "branchId";
    private static final String createQueryYear = "CREATE TABLE " + MST_YEAR +" (" +
            KEY_YEAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_YEAR + " TEXT, " +
            FKEY_BRANCH_ID + " INTEGER, FOREIGN KEY (" + FKEY_BRANCH_ID + ") REFERENCES " + MST_BRANCH + "(" + KEY_BRANCH_ID + "))";
    private static final String dropQueryYear = "DROP TABLE IF EXISTS " + MST_YEAR;

    private static final String MST_SEMESTER = "mst_semester";
    private static final String KEY_SEMESTER_ID = "semesterId";
    private static final String COL_SEMESTER = "semester";
    private static final String FKEY_YEAR_ID = "yearId";
    private static final String createQuerySemester = "CREATE TABLE "+ MST_SEMESTER + "(" +
            KEY_SEMESTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_SEMESTER + " TEXT, " +
            FKEY_YEAR_ID + " INTEGER, FOREIGN KEY(" + FKEY_YEAR_ID + ") REFERENCES " + MST_YEAR + " (" + KEY_YEAR_ID +"))";
    private static final String dropQuerySemester = "DROP TABLE IF EXISTS " + MST_SEMESTER;

    private static final String MST_SUBJECT = "mst_subject";
    private static final String KEY_SUBJECT_ID = "subjectId";
    private static final String COL_SUBJECT_CODE = "subjectCode";
    private static final String COL_SUBJECT_NAME = "subjectName";
    private static final String COL_SUBJECT_CREDITS = "subjectCredits";
    private static final String FKEY_SEMESTER_ID = "semesterId";
    private static final String createQuerySubject = "CREATE TABLE "+ MST_SUBJECT +" (" +
            KEY_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_SUBJECT_CODE + " TEXT, " +
            COL_SUBJECT_NAME + " TEXT, " +
            COL_SUBJECT_CREDITS + " TEXT, " +
            FKEY_SEMESTER_ID + " INTEGER, FOREIGN KEY(" + FKEY_SEMESTER_ID + ") REFERENCES " + MST_SEMESTER + "(" + KEY_SEMESTER_ID +"))";
    private static final String dropQuerySubject = "DROP TABLE IF EXISTS " + MST_SUBJECT;

    private static final String MST_USERDETAILS = "mst_userdetails";
    private static final String KEY_USER_ID = "userId";
    private static final String COL_FIRSTNAME = "firstName";
    private static final String COL_LASTNAME = "lastName";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_PHONE_NO = "phoneNumber";
    private static final String COL_GENDER = "gender";
    private static final String COL_ADDRESS = "address";
    private static final String COL_USER_TYPE = "userType";
    private static final String createQueryUser = "CREATE TABLE "+ MST_USERDETAILS +" (" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_FIRSTNAME + " TEXT, " +
            COL_LASTNAME + " TEXT, " +
            COL_EMAIL + " TEXT UNIQUE, " +
            COL_PASSWORD + " TEXT, " +
            COL_PHONE_NO + " TEXT, " +
            COL_GENDER + " TEXT, " +
            COL_ADDRESS + " TEXT, " +
            COL_USER_TYPE + " TEXT)";
    private static final String dropQueryUser = "DROP TABLE IF EXISTS " + MST_USERDETAILS;

    private static final String MST_STUDENT = "mst_student";
    private static final String KEY_ENROLLMENT_NUMBER = "enrollmentNumber";
    private static final String COL_ROLL_NUMBER = "rollNumber";
    private static final String COL_ADMISSION_YEAR = "admissionYear";
    private static final String COL_CURRENT_YEAR = "year";
    private static final String COL_CURRENT_SEMESTER = "semester";
    private static final String COL_DIVISION = "division";
//    private static final String FKEY_BRANCH_ID = "branchId";
    private static final String COL_BRANCH = "branchName";
    private static final String FKEY_USER_ID = "userId";
    private static final String createQueryStudent = "CREATE TABLE "+ MST_STUDENT +" (" +
            KEY_ENROLLMENT_NUMBER + " TEXT PRIMARY KEY, " +
            COL_ROLL_NUMBER + " TEXT, " +
            COL_ADMISSION_YEAR + " INTEGER, " +
            COL_CURRENT_YEAR + " TEXT, " +
            COL_CURRENT_SEMESTER + " TEXT, " +
            COL_DIVISION + " TEXT, " +
            FKEY_BRANCH_ID + " INTEGER, " +
    COL_BRANCH + " TEXT, " +
    FKEY_USER_ID + " INTEGER, FOREIGN KEY(" + FKEY_USER_ID + ") REFERENCES " + MST_USERDETAILS + "(" + KEY_USER_ID +")" +
           ", FOREIGN KEY(" + FKEY_BRANCH_ID + ") REFERENCES " + MST_BRANCH + " (" + KEY_BRANCH_ID +"))"  ;
    private static final String dropQueryStudent = "DROP TABLE IF EXISTS " + MST_STUDENT;

    private static final String MST_FACULTY = "mst_faculty";
    private static final String KEY_FACULTY_ID = "facultyId";
    private static final String COL_FACULTY_TYPE = "facultyType";
    private static final String COL_ACADEMIC_QUALIFICATION = "academicQualification";
    // private static final String FKEY_USER_ID = "userId";
    private static final String createQueryFaculty = "CREATE TABLE "+ MST_FACULTY +" (" +
            KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_FACULTY_TYPE + " TEXT, " +
            COL_ACADEMIC_QUALIFICATION + " TEXT, " +
            FKEY_USER_ID + " INTEGER, FOREIGN KEY(" + FKEY_USER_ID + ") REFERENCES " + MST_USERDETAILS + "(" + KEY_USER_ID +"))";
    private static final String dropQueryFaculty = "DROP TABLE IF EXISTS " + MST_FACULTY;

    private static final String TRN_FACULTY_SUBJECT_MAPPING = "trn_faculty_subject_mapping";
    private static final String KEY_FACULTY_SUBJECT_MAPPING_ID = "facultySubjectMappingId";
    private static final String FKEY_FACULTY_ID = "facultyId";
    private static final String FKEY_SUBJECT_ID = "subjectId";
    private static final String createQueryFacultySubjectMapping = "CREATE TABLE "+ TRN_FACULTY_SUBJECT_MAPPING +" (" +
            KEY_FACULTY_SUBJECT_MAPPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FKEY_FACULTY_ID + " INTEGER, " +
            FKEY_SUBJECT_ID + " INTEGER,"+
            " FOREIGN KEY(" + FKEY_FACULTY_ID + ") REFERENCES " + MST_FACULTY + "(" + KEY_FACULTY_ID +") ON UPDATE CASCADE ON DELETE CASCADE " +
            " ,FOREIGN KEY(" + FKEY_SUBJECT_ID + ") REFERENCES " + MST_SUBJECT + "(" + KEY_SUBJECT_ID +") ON UPDATE CASCADE ON DELETE CASCADE )";
    private static final String dropQueryFacultySubjectMapping = "DROP TABLE IF EXISTS " + TRN_FACULTY_SUBJECT_MAPPING;

    private static final String TRN_STUDENT_ATTENDANCE = "trn_student_attendance";
    private static final String KEY_ATTENDANCE_ID = "attendance_Id";
    private static final String FKEY_ENROLLMENT_NUMBER = "enrollmentNumber";
    // private static final String COL_ROLL_NUMBER = "rollNumber";
    // private static final String FKEY_FACULTY_ID = "facultyId";
    // private static final String FKEY_SUBJECT_ID = "subjectId";
    private static final String COL_DATE = "date";
    private static final String COL_DAY = "day";
    private static final String COL_MONTH = "month";
    private static final String COL_YEAR = "year";
    private static final String COL_ACADEMIC_YEAR = "academicYear";
    private static final String COL_ATTENDANCE_STATUS = "attendanceStatus";
    private static final String createQueryAttendance = "CREATE TABLE "+ TRN_STUDENT_ATTENDANCE +" (" +
            KEY_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FKEY_ENROLLMENT_NUMBER + " TEXT, "+
            COL_ROLL_NUMBER + " TEXT, " +
            COL_DIVISION + " TEXT, " +
            FKEY_FACULTY_ID + " INTEGER,"+
            FKEY_SUBJECT_ID + " INTEGER," +
            COL_DATE + " DATE, " +
            COL_DAY + " INTEGER, " +
            COL_MONTH + " INTEGER, " +
            COL_YEAR + " INTEGER, " +
            COL_ACADEMIC_YEAR + " TEXT, " +
            COL_ATTENDANCE_STATUS + " TEXT"
            + ", FOREIGN KEY(" + FKEY_ENROLLMENT_NUMBER + ") REFERENCES " + MST_STUDENT + "(" + KEY_ENROLLMENT_NUMBER +") ON UPDATE CASCADE ON DELETE CASCADE "
            + ", FOREIGN KEY(" + FKEY_FACULTY_ID + ") REFERENCES " + MST_FACULTY + "(" + KEY_FACULTY_ID +") ON UPDATE CASCADE ON DELETE SET NULL "
            + ", FOREIGN KEY(" + FKEY_SUBJECT_ID + ") REFERENCES " + MST_SUBJECT + "(" + KEY_SUBJECT_ID +")"
            + ")";
    private static final String dropQueryAttendance = "DROP TABLE IF EXISTS " + TRN_STUDENT_ATTENDANCE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // checking database and open it if exists
        if (!checkDataBase()) {
            try {
                this.getReadableDatabase();
                copyDataBase(context);
                this.close();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context, "Initial database is created", Toast.LENGTH_LONG).show();
        }
    }

    private void copyDataBase(Context context) throws IOException{
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        OutputStream myOutput = new FileOutputStream(dbPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.v("db log", "database does't exist");
        }

        if (checkDB != null) {
            exist = true;
            checkDB.close();
        }
        return exist;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("createQueryBranch", createQueryBranch);
        Log.d("createQueryYear", createQueryYear);
        Log.d("createQuerySemester", createQuerySemester);
        Log.d("createQuerySubject", createQuerySubject);
        Log.d("createQueryUser", createQueryUser);
        Log.d("createQueryStudent", createQueryStudent);
        Log.d("createQueryFaculty", createQueryFaculty);
        Log.d("createQueryMapping", createQueryFacultySubjectMapping);
        Log.d("createQueryAttendance", createQueryAttendance);

        try
        {
            db.execSQL(createQueryBranch);
            db.execSQL(createQueryYear);
            db.execSQL(createQuerySemester);
            db.execSQL(createQuerySubject);
            db.execSQL(createQueryUser);
            db.execSQL(createQueryStudent);
            db.execSQL(createQueryFaculty);
            db.execSQL(createQueryFacultySubjectMapping);
            db.execSQL(createQueryAttendance);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try
        {
            db.execSQL(dropQueryBranch);
            db.execSQL(dropQueryYear);
            db.execSQL(dropQuerySemester);
            db.execSQL(dropQuerySubject);
            db.execSQL(dropQueryUser);
            db.execSQL(dropQueryStudent);
            db.execSQL(dropQueryFaculty);
            db.execSQL(dropQueryFacultySubjectMapping);
            db.execSQL(dropQueryAttendance);

            onCreate(db);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    public void startDB() {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public List<SubjectBean> getAllSubjects() {
        ArrayList<SubjectBean> list = new ArrayList<SubjectBean>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_SUBJECT;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                SubjectBean subjectBean = new SubjectBean();
                subjectBean.setSubjectId(Integer.parseInt(cursor.getString(0)));
                subjectBean.setSubjectCode(cursor.getString(1));
                subjectBean.setSubjectName(cursor.getString(2));
                subjectBean.setSubjectCredits(cursor.getString(3));
                subjectBean.setSemesterId(Integer.parseInt(cursor.getString(4)));
                list.add(subjectBean);
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Subjects: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public List<SubjectBean> getAllAssignedSubjects(int facultyId) {
        ArrayList<SubjectBean> list = new ArrayList<SubjectBean>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT M.* FROM " + MST_SUBJECT + " AS M INNER JOIN " + TRN_FACULTY_SUBJECT_MAPPING + " AS T ON M."
                + KEY_SUBJECT_ID + "=T." + FKEY_SUBJECT_ID + " WHERE T." + KEY_FACULTY_ID + "=" + facultyId;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                SubjectBean subjectBean = new SubjectBean();
                subjectBean.setSubjectId(Integer.parseInt(cursor.getString(0)));
                subjectBean.setSubjectCode(cursor.getString(1));
                subjectBean.setSubjectName(cursor.getString(2));
                subjectBean.setSubjectCredits(cursor.getString(3));
                subjectBean.setSemesterId(Integer.parseInt(cursor.getString(4)));
                list.add(subjectBean);
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Subjects: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    @SuppressLint("LongLogTag")
    public MappingHelperBean getAllAssignedSubjectsSpinner(int facultyId) {
        MappingHelperBean list = new MappingHelperBean();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT M.* FROM " + MST_SUBJECT + " AS M INNER JOIN " + TRN_FACULTY_SUBJECT_MAPPING + " AS T ON M."
                + KEY_SUBJECT_ID + "=T." + FKEY_SUBJECT_ID + " WHERE T." + KEY_FACULTY_ID + "=" + facultyId;
        Cursor cursor = db.rawQuery(query, null);

        List<Integer> ids = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                ids.add(cursor.getInt(0));
                subjects.add(cursor.getString(2));
            } while(cursor.moveToNext());
        }

        list.setIds(ids);
        list.setValues(subjects);

        System.out.println(list);
        Log.d("Subject (Spinner) (Faculty): ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public UserDetailsBean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?";

        Cursor cursor = db.rawQuery(query, new String[] {username, password});

        if(cursor.moveToFirst()) {
            UserDetailsBean userDetailsBean = new UserDetailsBean();
            userDetailsBean.setUserId(cursor.getInt(0));
            userDetailsBean.setFirstName(cursor.getString(1));
            userDetailsBean.setLastName(cursor.getString(2));
            userDetailsBean.setEmail(cursor.getString(3));
            userDetailsBean.setPassword(cursor.getString(4));
            userDetailsBean.setPhoneNumber(cursor.getString(5));
            userDetailsBean.setGender(cursor.getString(6));
            userDetailsBean.setAddress(cursor.getString(7));
            userDetailsBean.setUserType(cursor.getString(8));
            return userDetailsBean;
        } else {
            return null;
        }
    }

    // Faculty
    public List<ViewFacultyBean> getAllFaculty() {
        ArrayList<ViewFacultyBean> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + COL_USER_TYPE + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { "Faculty" });

        if(cursor.moveToFirst())
        {
            do{
                ViewFacultyBean viewFacultyBean = new ViewFacultyBean();
                viewFacultyBean.setUserId(cursor.getInt(0));
                viewFacultyBean.setFirstName(cursor.getString(1));
                viewFacultyBean.setLastName(cursor.getString(2));
                viewFacultyBean.setEmail(cursor.getString(3));
                viewFacultyBean.setPassword(cursor.getString(4));
                viewFacultyBean.setPhoneNumber(cursor.getString(5));
                viewFacultyBean.setGender(cursor.getString(6));
                viewFacultyBean.setAddress(cursor.getString(7));
                viewFacultyBean.setUserType(cursor.getString(8));

                Cursor cursorFaculty = db.rawQuery("SELECT * FROM " + MST_FACULTY + " WHERE " + KEY_USER_ID + "=" + viewFacultyBean.getUserId(), null);
                if(cursorFaculty.moveToFirst()) {
                    viewFacultyBean.setFacultyId(cursorFaculty.getInt(0));
                    viewFacultyBean.setFacultyType(cursorFaculty.getString(1));
                    viewFacultyBean.setAcademicQualification(cursorFaculty.getString(2));
                } else {
                    viewFacultyBean = null;
                }
                list.add(viewFacultyBean);
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Faculty: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public ViewFacultyBean initializeFacultyDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + KEY_USER_ID + "=" +  userId;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                ViewFacultyBean viewFacultyBean = new ViewFacultyBean();
                viewFacultyBean.setUserId(cursor.getInt(0));
                viewFacultyBean.setFirstName(cursor.getString(1));
                viewFacultyBean.setLastName(cursor.getString(2));
                viewFacultyBean.setEmail(cursor.getString(3));
                viewFacultyBean.setPassword(cursor.getString(4));
                viewFacultyBean.setPhoneNumber(cursor.getString(5));
                viewFacultyBean.setGender(cursor.getString(6));
                viewFacultyBean.setAddress(cursor.getString(7));
                viewFacultyBean.setUserType(cursor.getString(8));

                Cursor cursorFaculty = db.rawQuery("SELECT * FROM " + MST_FACULTY + " WHERE " + KEY_USER_ID + "=" + viewFacultyBean.getUserId(), null);
                if(cursorFaculty.moveToFirst()) {
                    viewFacultyBean.setFacultyId(cursorFaculty.getInt(0));
                    viewFacultyBean.setFacultyType(cursorFaculty.getString(1));
                    viewFacultyBean.setAcademicQualification(cursorFaculty.getString(2));
                } else {
                    viewFacultyBean = null;
                }

                return viewFacultyBean;
            }while(cursor.moveToNext());
        }

        return null;
    }

    public boolean deleteFaculty(ViewFacultyBean facultyBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        String query = "DELETE FROM " + MST_FACULTY + " WHERE " + KEY_USER_ID + "=" + facultyBean.getUserId();
        Cursor cursor = db.rawQuery(query, null);
        query = "DELETE FROM " + MST_USERDETAILS + " WHERE " + KEY_USER_ID + "=" + facultyBean.getUserId();
        if(cursor.moveToFirst()) {
            return false;
        } else {
            cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public long insertUser(String firstName, String lastName, String email, String password, String phoneNumber, String gender, String address, String userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        ContentValues values = new ContentValues();
        values.put(COL_FIRSTNAME, firstName);
        values.put(COL_LASTNAME, lastName);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        values.put(COL_PHONE_NO, phoneNumber);
        values.put(COL_GENDER, gender);
        values.put(COL_ADDRESS, address);
        values.put(COL_USER_TYPE, userType);

        long res = db.insert(MST_USERDETAILS, null, values);
        db.close();
        return res;
    }

    public long insertFaculty(String facultyType, String academicQualification, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        ContentValues values = new ContentValues();
        values.put(COL_FACULTY_TYPE, facultyType);
        values.put(COL_ACADEMIC_QUALIFICATION, academicQualification);
        values.put(FKEY_USER_ID, userId);

        long res = db.insert(MST_FACULTY, null, values);
        db.close();
        return res;
    }

    public ViewFacultyBean addFaculty(ViewFacultyBean facultyBean) {
        long userId = insertUser(facultyBean.getFirstName(),
                facultyBean.getLastName(),
                facultyBean.getEmail(),
                facultyBean.getPassword(),
                facultyBean.getPhoneNumber(),
                facultyBean.getGender(),
                facultyBean.getAddress(), userType.get(1));

        if(userId == -1) {
            return null;
        }

        long facultyId = insertFaculty(facultyBean.getFacultyType(), facultyBean.getAcademicQualification(), userId);

        if(facultyId == -1) {
            return null;
        }

        return initializeFacultyDetails((int) userId);
    }

    // Student
    public List<ViewStudentBean> getAllStudent() {
        ArrayList<ViewStudentBean> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + COL_USER_TYPE + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { userType.get(2) });

        if(cursor.moveToFirst())
        {
            do{
                ViewStudentBean viewStudentBean = new ViewStudentBean();
                viewStudentBean.setUserId(cursor.getInt(0));
                viewStudentBean.setFirstName(cursor.getString(1));
                viewStudentBean.setLastName(cursor.getString(2));
                viewStudentBean.setEmail(cursor.getString(3));
                viewStudentBean.setPassword(cursor.getString(4));
                viewStudentBean.setPhoneNumber(cursor.getString(5));
                viewStudentBean.setGender(cursor.getString(6));
                viewStudentBean.setAddress(cursor.getString(7));
                viewStudentBean.setUserType(cursor.getString(8));

                Cursor cursorStudent = db.rawQuery("SELECT * FROM " + MST_STUDENT + " WHERE " + KEY_USER_ID + "=" + viewStudentBean.getUserId(), null);
                if(cursorStudent.moveToFirst()) {
                    viewStudentBean.setEnrollmentNumber(cursorStudent.getString(0));
                    viewStudentBean.setRollNumber(cursorStudent.getString(1));
                    viewStudentBean.setAdmissionYear(cursorStudent.getInt(2));
                    viewStudentBean.setYear(cursorStudent.getString(3));
                    viewStudentBean.setSemester(cursorStudent.getString(4));
                    viewStudentBean.setDivision(cursorStudent.getString(5));
                    viewStudentBean.setBranchId(cursorStudent.getInt(6));
                    viewStudentBean.setBranchName(cursorStudent.getString(7));
                } else {
                    viewStudentBean = null;
                }
                list.add(viewStudentBean);
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Student: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public List<ViewFacultySubjectMappingBean> getAllFacultySubjectMappings() {
        List<ViewFacultySubjectMappingBean> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TRN_FACULTY_SUBJECT_MAPPING;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                Cursor cursorFaculty = db.rawQuery("SELECT "+ KEY_USER_ID +" FROM " + MST_FACULTY + " WHERE " + KEY_FACULTY_ID + "=" + cursor.getInt(1), null);
                if(cursorFaculty.moveToFirst()) {
                    ViewFacultySubjectMappingBean bean = new ViewFacultySubjectMappingBean(initializeFacultyDetails(cursorFaculty.getInt(0)), initializeSubjectDetails(cursor.getInt(2)));
                    list.add(bean);
                }
            } while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Mapping: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    private SubjectBean initializeSubjectDetails(int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_SUBJECT + " WHERE " +  KEY_SUBJECT_ID + "=" +  subjectId;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                SubjectBean bean = new SubjectBean();
                bean.setSubjectId(cursor.getInt(0));
                bean.setSubjectCode(cursor.getString(1));
                bean.setSubjectName(cursor.getString(2));
                bean.setSubjectCredits(cursor.getString(3));
                bean.setSemesterId(cursor.getInt(4));
                return bean;
            }while(cursor.moveToNext());
        }

        return null;
    }

    public ViewStudentBean initializeStudentDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + KEY_USER_ID + "=" +  userId;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                ViewStudentBean viewStudentBean = new ViewStudentBean();
                viewStudentBean.setUserId(cursor.getInt(0));
                viewStudentBean.setFirstName(cursor.getString(1));
                viewStudentBean.setLastName(cursor.getString(2));
                viewStudentBean.setEmail(cursor.getString(3));
                viewStudentBean.setPassword(cursor.getString(4));
                viewStudentBean.setPhoneNumber(cursor.getString(5));
                viewStudentBean.setGender(cursor.getString(6));
                viewStudentBean.setAddress(cursor.getString(7));
                viewStudentBean.setUserType(cursor.getString(8));

                Cursor cursorStudent = db.rawQuery("SELECT * FROM " + MST_STUDENT + " WHERE " + KEY_USER_ID + "=" + viewStudentBean.getUserId(), null);
                if(cursorStudent.moveToFirst()) {
                    viewStudentBean.setEnrollmentNumber(cursorStudent.getString(0));
                    viewStudentBean.setRollNumber(cursorStudent.getString(1));
                    viewStudentBean.setAdmissionYear(cursorStudent.getInt(2));
                    viewStudentBean.setYear(cursorStudent.getString(3));
                    viewStudentBean.setSemester(cursorStudent.getString(4));
                    viewStudentBean.setDivision(cursorStudent.getString(5));
                    viewStudentBean.setBranchId(cursorStudent.getInt(6));
                    viewStudentBean.setBranchName(cursorStudent.getString(7));
                } else {
                    viewStudentBean = null;
                }

                return viewStudentBean;
            } while(cursor.moveToNext());
        }

        return null;
    }

    public boolean deleteStudent(ViewStudentBean viewStudentBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        String query = "DELETE FROM " + MST_STUDENT + " WHERE " + KEY_USER_ID + "=" + viewStudentBean.getUserId();
        Cursor cursor = db.rawQuery(query, null);
        query = "DELETE FROM " + MST_USERDETAILS + " WHERE " + KEY_USER_ID + "=" + viewStudentBean.getUserId();
        if(cursor.moveToFirst()) {
            return false;
        } else {
            cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public long insertStudent(String enrollmentNumber, String rollNumber, int admissionYear, String year, String semester, String division, String branchName, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        Log.e("Branch Name:", branchName);
        String query = "SELECT * FROM " + MST_BRANCH + " WHERE " + COL_BRANCH_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { branchName });

        ContentValues values = new ContentValues();
        values.put(KEY_ENROLLMENT_NUMBER, enrollmentNumber);
        values.put(COL_ROLL_NUMBER, rollNumber);
        values.put(COL_ADMISSION_YEAR, admissionYear);
        values.put(COL_YEAR, year);
        values.put(COL_SEMESTER, semester);
        values.put(COL_DIVISION, division);

        if(cursor.moveToFirst()) {
            values.put(KEY_BRANCH_ID, cursor.getInt(0));
            values.put(COL_BRANCH_NAME, branchName);
        }
        values.put(FKEY_USER_ID, userId);

        long res = db.insert(MST_STUDENT, null, values);
        db.close();
        return res;
    }

    public ViewStudentBean addStudent(ViewStudentBean studentBean) {
        long userId = insertUser(studentBean.getFirstName(),
                studentBean.getLastName(),
                studentBean.getEmail(),
                studentBean.getPassword(),
                studentBean.getPhoneNumber(),
                studentBean.getGender(),
                studentBean.getAddress(),
                userType.get(2));

        if(userId == -1) {
            return null;
        }

        long row = insertStudent(
                studentBean.getEnrollmentNumber(),
                studentBean.getRollNumber(),
                studentBean.getAdmissionYear(),
                studentBean.getYear(),
                studentBean.getSemester(),
                studentBean.getDivision(),
                studentBean.getBranchName()
                ,userId);

        if(row == -1) {
            return null;
        }

        return initializeStudentDetails((int) userId);
    }

    public boolean addFacultySubjectMapping(int facultyId, int subjectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        String query = "DELETE FROM " + TRN_FACULTY_SUBJECT_MAPPING + " WHERE " + KEY_SUBJECT_ID + "=" + subjectId;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            db.close();
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(KEY_SUBJECT_ID, subjectId);
            values.put(KEY_FACULTY_ID, facultyId);

            long res = db.insert(TRN_FACULTY_SUBJECT_MAPPING, null, values);
            db.close();
            return (res == -1) ? false : true;
        }
    }

    public boolean deleteFacultySubjectMapping(ViewFacultySubjectMappingBean viewFacultySubjectMappingBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TRN_FACULTY_SUBJECT_MAPPING + " WHERE " + KEY_SUBJECT_ID + "=" + viewFacultySubjectMappingBean.getSubjectBean().getSubjectId() + " AND " + KEY_FACULTY_ID + "=" + viewFacultySubjectMappingBean.getViewFacultyBean().getFacultyId();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getAllYear() {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_YEAR + " WHERE " + FKEY_BRANCH_ID + "=1";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Year: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public List<String> getAllSemeseter() {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_SEMESTER;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Semester: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public List<String> getAllSemeseterByYear(String year) {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_SEMESTER + " FROM " + MST_SEMESTER + " WHERE " + FKEY_YEAR_ID + "=(SELECT "+ KEY_YEAR_ID +" FROM "+ MST_YEAR +" WHERE "+ COL_YEAR + "=?" + ")";
        Cursor cursor = db.rawQuery(query,  new String[] { year });

        if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Semester (Filter): ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public List<String> getAllBranch() {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_BRANCH +" FROM " + MST_BRANCH;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        System.out.println(list);
        Log.d("Branch: ", String.valueOf(list));
        cursor.close();
        return list;
    }

    public MappingHelperBean getAllSubject() {
        MappingHelperBean list = new MappingHelperBean();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_SUBJECT;
        Cursor cursor = db.rawQuery(query, null);

        List<Integer> ids = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                ids.add(cursor.getInt(0));
                subjects.add(cursor.getString(2));
            } while(cursor.moveToNext());
        }

        list.setIds(ids);
        list.setValues(subjects);

        System.out.println(list);
        Log.d("Subject (Spinner): ", String.valueOf(list));
        return list;
    }

    public MappingHelperBean getAllFacultySpinner() {
        MappingHelperBean list = new MappingHelperBean();

        List<ViewFacultyBean> viewFacultyBeans = getAllFaculty();

        List<Integer> ids = new ArrayList<>();
        List<String> faculties = new ArrayList<>();

        for (int i=0; i<viewFacultyBeans.size(); i++)
        {
            ids.add(viewFacultyBeans.get(i).getFacultyId());
            faculties.add(viewFacultyBeans.get(i).getFirstName() + " " + viewFacultyBeans.get(i).getLastName());
        }

        list.setIds(ids);
        list.setValues(faculties);

        System.out.println(list);
        Log.d("Faculty (Spinner): ", String.valueOf(list));
        return list;
    }

    public String getYearSemesterBySemId(int semesterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT A." + COL_YEAR + ", B." + COL_SEMESTER + " FROM " + MST_YEAR + " AS A" + " INNER JOIN " +
                MST_SEMESTER + " AS B ON A." + KEY_YEAR_ID + "=B." + FKEY_YEAR_ID + " WHERE " + KEY_SEMESTER_ID + "=" + semesterId;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            return cursor.getString(0) + "- " + cursor.getString(1);
        } else {
            return new String();
        }
    }

    public List<String> getYearSemesterBySubId(int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT A." + COL_YEAR + ", B." + COL_SEMESTER + " FROM " + MST_YEAR + " AS A" + " INNER JOIN " +
                MST_SEMESTER + " AS B ON A." + KEY_YEAR_ID + "=B." + FKEY_YEAR_ID + " INNER JOIN "+
                MST_SUBJECT + " AS C ON B." + KEY_SEMESTER_ID + "=C." + FKEY_SEMESTER_ID + " WHERE " + KEY_SUBJECT_ID + "=" + subjectId;
        Cursor cursor = db.rawQuery(query, null);

        List<String> res = new ArrayList<>();
        if(cursor.moveToFirst()) {
            res.add(cursor.getString(0));
            res.add(cursor.getString(1));
            return res;
        } else {
            return res;
        }
    }

    public List<MarkAttendanceStudentBean> getStudentByYearSemDiv(String year, String semester, String division) {
        List<MarkAttendanceStudentBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int branchId = getBranchByYear(year);
        if(branchId == -1) { return null; }

        String query = "SELECT " + KEY_USER_ID + " FROM " + MST_STUDENT + " WHERE " + COL_YEAR + "=? AND "+ COL_SEMESTER + "=? AND "
                + COL_DIVISION + "=?" + " AND " + FKEY_BRANCH_ID + "=" + branchId;
        Cursor cursor = db.rawQuery(query, new String[] { year, semester, division});

        if(cursor.moveToFirst())
        {
            do{
                list.add(new MarkAttendanceStudentBean(initializeStudentDetails(cursor.getInt(0))));
            } while(cursor.moveToNext());
        }

        return list;
    }

    public int getBranchByYear(String year) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT B." + KEY_BRANCH_ID + " FROM " + MST_BRANCH +
                " AS B INNER JOIN " + MST_YEAR + " AS Y ON Y." + KEY_BRANCH_ID + "=B." + KEY_BRANCH_ID
                + " WHERE Y." + COL_YEAR + "='"+ year +"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return -1;
        }
    }

    public boolean insertAttendance(List<MarkAttendanceStudentBean> students, String division, int subjectId, int facultyId, String date, int day, int month, int yearInt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        String query = "DELETE FROM " + TRN_STUDENT_ATTENDANCE + " WHERE " + COL_DIVISION + "='" + division + "' AND " +
                KEY_SUBJECT_ID + "=" + subjectId + " AND DATE(" + COL_DATE + ")=" + "DATE('" + date + "')";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            db.close();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_ID, subjectId);
        if(facultyId != -1) {
            values.put(FKEY_FACULTY_ID, facultyId);
        }
        values.put(COL_DIVISION, division);
        values.put(COL_DATE, date);
        values.put(COL_DAY, day);
        values.put(COL_MONTH, month);
        values.put(COL_YEAR, yearInt);
        values.put(COL_ACADEMIC_YEAR, String.valueOf(yearInt));

        for(int i=0; i<students.size(); i++) {
            values.put(KEY_ENROLLMENT_NUMBER, students.get(i).getStudentBean().getEnrollmentNumber());
            values.put(COL_ROLL_NUMBER, students.get(i).getStudentBean().getRollNumber());
            String attendanceStatus = (students.get(i).isChecked()) ? "Present" : "Absent";
            values.put(COL_ATTENDANCE_STATUS, attendanceStatus);

            long res = db.insert(TRN_STUDENT_ATTENDANCE, null, values);
            if (res == -1) {
                return false;
            }
        }

        db.close();
        return true;
    }

    @SuppressLint("LongLogTag")
    public List<String> uploadExcelFileToDatabase(Context context, String filePath, int subjectId, int facultyId, String date, int day, int month, int yearInt) {
        SQLiteDatabase db = this.getWritableDatabase();
        DataFormatter formatter = new DataFormatter();
        db.execSQL("PRAGMA foreign_keys=ON");

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_ID, subjectId);
        if(facultyId != -1) {
            values.put(FKEY_FACULTY_ID, facultyId);
        }

        values.put(COL_DATE, date);
        values.put(COL_DAY, day);
        values.put(COL_MONTH, month);
        values.put(COL_YEAR, yearInt);
        values.put(COL_ACADEMIC_YEAR, String.valueOf(yearInt));

        int successCnt = 0;
        int unSuccessCnt = 0;
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            // Check if the headers are present and in the correct order
            Row headerRow = sheet.getRow(0);
            if (headerRow != null && areHeadersValid(headerRow)) {
                SQLiteDatabase database = this.getWritableDatabase();
                database.beginTransaction();

                try {
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row currentRow = sheet.getRow(i);

                        try {
                            String enrollmentNumber = formatter.formatCellValue(currentRow.getCell(0));
                            String rollNumber = formatter.formatCellValue(currentRow.getCell(1));
                            String division = currentRow.getCell(2).getStringCellValue().trim().toUpperCase();
                            String attendanceStatus = currentRow.getCell(3).getStringCellValue().trim().toLowerCase();
                            attendanceStatus = attendanceStatus.substring(0, 1).toUpperCase() + attendanceStatus.substring(1);
                            ViewStudentBean student = getStudentByEnrollmentNumber(enrollmentNumber);
                            if (student == null) {
                                unSuccessCnt++;
                                continue;
                            }

                            if (!student.getRollNumber().equals(rollNumber)
                                    || !student.getDivision().equals(division)) {
                                System.out.println("1");
                                unSuccessCnt++;
                                continue;
                            }

                            if(!(attendanceStatus.equalsIgnoreCase("Present") || attendanceStatus.equalsIgnoreCase("Absent"))) {
                                System.out.println("2");
                                unSuccessCnt++;
                                continue;
                            }

                            List<String> yearSem = getYearSemesterBySubId(subjectId);
                            if (yearSem != null && !(yearSem.get(0).equalsIgnoreCase(student.getYear())
                                    && yearSem.get(1).equalsIgnoreCase(student.getSemester()))) {
                                System.out.println("1");
                                unSuccessCnt++;
                                continue;
                            }

                            String query = "DELETE FROM " + TRN_STUDENT_ATTENDANCE + " WHERE " +
                                    KEY_SUBJECT_ID + "=" + subjectId + " AND DATE(" + COL_DATE + ")=" + "DATE('" + date + "') AND " +
                                    KEY_ENROLLMENT_NUMBER + "='" + enrollmentNumber + "'";
                            Cursor cursor = database.rawQuery(query, null);

                            if (cursor.moveToFirst()) {
                                System.out.println("3");
                                unSuccessCnt++;
                                continue;
                            }

                            values.put(KEY_ENROLLMENT_NUMBER, enrollmentNumber);
                            values.put(COL_ROLL_NUMBER, rollNumber);
                            values.put(COL_DIVISION, division);
                            values.put(COL_ATTENDANCE_STATUS, attendanceStatus);

                            long res = database.insert(TRN_STUDENT_ATTENDANCE, null, values);
                            if (res == -1) {
                                System.out.println("4");
                                unSuccessCnt++;
                            } else {
                                successCnt++;
                            }
                        } catch (Exception e) {
							e.printStackTrace();
							System.out.println(e);
                            unSuccessCnt++;
                        }
                    }

                    database.setTransactionSuccessful();
                    Toast.makeText(context, "Data imported successfully", Toast.LENGTH_SHORT).show();
                } finally {
                    database.endTransaction();
                    database.close();
                    workbook.close();
                }
            }
        } catch (IOException e) {
            Log.e( "Error reading Excel file", String.valueOf(e));
            Toast.makeText(context, "Error reading Excel file", Toast.LENGTH_SHORT).show();
        }
        db.close();

        List<String> lst = new ArrayList<>();
        lst.add(String.valueOf(successCnt));
        lst.add(String.valueOf(unSuccessCnt));
        return lst;
    }

    private boolean areHeadersValid(Row headerRow) {
        // Check if headers are present and in the correct order
        return headerRow != null &&
                isHeaderValid(headerRow.getCell(0), "Enrollment Number") &&
                isHeaderValid(headerRow.getCell(1), "Roll Number") &&
                isHeaderValid(headerRow.getCell(2), "Division") &&
                isHeaderValid(headerRow.getCell(3), "Status");
    }

    private boolean isHeaderValid(Cell cell, String expectedHeader) {
        return cell != null && cell.getStringCellValue().equalsIgnoreCase(expectedHeader);
    }

    public ViewStudentBean getStudentByEnrollmentNumber(String enrollmentNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_USER_ID + " FROM " + MST_STUDENT + " WHERE " + KEY_ENROLLMENT_NUMBER + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { enrollmentNumber });
        if(cursor.moveToFirst()) {
            return initializeStudentDetails(cursor.getInt(0));
        } else {
            return null;
        }
    }

    public boolean isUniqueEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        long numRows = DatabaseUtils.queryNumEntries(db, MST_USERDETAILS, COL_EMAIL + "=?", new String[] { email });
        Log.d("Email Rows: ", String.valueOf(numRows));
        return (numRows == 0) ? true : false;
    }

    public boolean isUniqueEnrollmentNumber(String enrollmentNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        long numRows = DatabaseUtils.queryNumEntries(db, MST_STUDENT, KEY_ENROLLMENT_NUMBER + "=?", new String[] { enrollmentNumber });
        Log.d("EnrollmentNumber Rows: ", String.valueOf(numRows));
        return (numRows == 0) ? true : false;
    }

    public boolean isUniqueRollNo(String rollNumber, String year, String semester) {
        SQLiteDatabase db = this.getReadableDatabase();
        long numRows = DatabaseUtils.queryNumEntries(db, MST_STUDENT, COL_ROLL_NUMBER + "=? AND " + COL_YEAR + "=? AND " + COL_SEMESTER + "=?", new String[] { rollNumber, year, semester });
        Log.d("Roll Number Rows: ", String.valueOf(numRows));
        return (numRows == 0) ? true : false;
    }

    public int isSubjectAssigned(int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_FACULTY_ID + " FROM " + TRN_FACULTY_SUBJECT_MAPPING + " WHERE " + KEY_SUBJECT_ID + "=" + subjectId;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return -1;
        }
    }

    public List<ViewAttendanceBean> getAttendance(StudentAttendanceBean attendanceBean, String date) {
        List<ViewAttendanceBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int branchId = getBranchByYear(attendanceBean.getAcademicYear());
        if(branchId == -1) { return null; }

        String query = "SELECT * FROM " + TRN_STUDENT_ATTENDANCE + " WHERE " +
                KEY_SUBJECT_ID + "=" + attendanceBean.getSubjectId() +
                " AND DATE(" + COL_DATE + ")=" + "DATE('" + date + "') ";

        if(attendanceBean.getDivision() != null) {
            query += "AND " + COL_DIVISION + "='" + attendanceBean.getDivision() + "' ";
        }

//        if(attendanceBean.getFacultyId() != -1) {
//            query += "AND " + KEY_FACULTY_ID + "=" + attendanceBean.getFacultyId() + " ";
//        }

        System.out.println(query);
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                ViewAttendanceBean viewAttendanceBean = new ViewAttendanceBean();
                StudentAttendanceBean studentAttendanceBean = new StudentAttendanceBean();
                studentAttendanceBean.setAttendanceId(cursor.getInt(0));
                studentAttendanceBean.setEnrollmentNumber(cursor.getString(1));
                studentAttendanceBean.setRollNumber(cursor.getString(2));
                studentAttendanceBean.setDivision(cursor.getString(3));
                studentAttendanceBean.setFacultyId(cursor.getInt(4));
                studentAttendanceBean.setSubjectId(cursor.getInt(5));
                studentAttendanceBean.setDay(cursor.getInt(7));
                studentAttendanceBean.setMonth(cursor.getInt(8));
                studentAttendanceBean.setYear(cursor.getInt(9));
                studentAttendanceBean.setAcademicYear(cursor.getString(10));
                studentAttendanceBean.setAttendanceStatus(cursor.getString(11));

                viewAttendanceBean.setAttendanceBean(studentAttendanceBean);
                viewAttendanceBean.setDate(date);

                query = "SELECT U." + COL_FIRSTNAME + "|| ' ' || U." + COL_LASTNAME + " FROM " + MST_USERDETAILS + " AS U " +
                        " INNER JOIN " + MST_STUDENT + " AS S ON S." + KEY_USER_ID + "=U." + KEY_USER_ID
                        + " WHERE S." + KEY_ENROLLMENT_NUMBER + "='"+ studentAttendanceBean.getEnrollmentNumber() +"'";
                Cursor cursorS = db.rawQuery(query, null);
                if(cursorS.moveToFirst()) {
                    viewAttendanceBean.setStudentName(cursorS.getString(0));
                }

                if(attendanceBean.getFacultyId() != -1) {
                    query = "SELECT U." + COL_FIRSTNAME + "|| ' ' || U." + COL_LASTNAME + " FROM " + MST_USERDETAILS + " AS U " +
                            " INNER JOIN " + MST_FACULTY + " AS F ON F." + KEY_USER_ID + "=U." + KEY_USER_ID
                            + " WHERE F." + KEY_FACULTY_ID + "=" + studentAttendanceBean.getFacultyId();
                    Cursor cursorF = db.rawQuery(query, null);
                    if(cursorF.moveToFirst()) {
                        viewAttendanceBean.setFacultyName(cursorF.getString(0));
                    }
                }

                // System.out.println(viewAttendanceBean);
                list.add(viewAttendanceBean);
            } while(cursor.moveToNext());
        }

        db.close();
        System.out.println(list);
        Log.d("View Attendance ", String.valueOf(list));
        return list;
    }
	
	public boolean checkUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MST_USERDETAILS + " WHERE " + COL_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {username});

        if(cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
	
    public int resetpassword(String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASSWORD,password);
        return db.update("MST_USERDETAILS",contentValues,COL_EMAIL + "=?",new String[]{email});
    }
}
