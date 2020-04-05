package ca.mealbell.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {


    /**
     * private int id;
     * private String title;
     * private String image;
     * private String summary;
     * private String instructions;
     * private Ingredient[] extendedIngredients;
     * private Equipement[] equipments;
     * private int readyInMinutes;
     * private int servings;
     */
    // DATABASE
    private final static String DATABASE = "mealbell";
    private final static int VERSION = 1;


    // TABLES
    private final String TABLE_RECIPES = "recipes";
    private final String TABLE_INGREDIENTS = "ingredients";
    private final String TABLE_EQUIPMENTS = "equipments";
    private final String TABLE_MEAL_PLANS = "meal_plans";
    private final String TABLE_MEALS = "meals";

    // COLUMNS
    private final String COLUMN_ID = "id";
    private final String COLUMN_TITLE = "title";
    private final String COLUMN_IMAGE = "image";
    private final String COLUMN_SUMMARY = "summary";
    private final String COLUMN_INSTRUCTIONS = "instructions";
    private final String COLUMN_READY_IN_MINUTES = "ready_in_minutes";
    private final String COLUMN_SERVINGS = "servings";


    // CREATE RECIPE TABLE
    private final String CREATE_RECIPES_TABLE = String.format("CREATE TABLE %s IF NOT EXISTS (" +
            "%s INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "%s VARCHAR(255)," +
            "%s VARCHAR(255)," +
            "%s TEXT," +
            "%s TEXT," +
            "%s INTEGER," +
            "%s INTEGER)", TABLE_RECIPES, COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE, COLUMN_SUMMARY, COLUMN_INSTRUCTIONS, COLUMN_READY_IN_MINUTES, COLUMN_SERVINGS);


    private static DatabaseHandler instance;

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context);
        return instance;
    }


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_RECIPES_TABLE);

        // Create tables again
        onCreate(db);
    }
}
