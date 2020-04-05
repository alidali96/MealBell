package ca.mealbell.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ca.mealbell.javabeans.RecipeInformation;

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
    private final String CREATE_RECIPES_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (" +
            "%s INTEGER PRIMARY KEY," +
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


    public List<RecipeInformation> getAllRecipes() {
        List<RecipeInformation> recipes = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_RECIPES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String image = cursor.getString(2);
            String summary = cursor.getString(3);
            String instructions = cursor.getString(4);
            int readyInMinutes = cursor.getInt(5);
            int servings = cursor.getInt(6);

            //TODO: Add Ingredients and Equipments
            RecipeInformation recipe = new RecipeInformation(id, title, image, summary, instructions, null, null, readyInMinutes, servings);
            recipes.add(recipe);
        }
        cursor.close();
        db.close();
        return recipes;
    }

    public void addRecipe(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, recipe.getId());
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_IMAGE, recipe.getImage());
        values.put(COLUMN_SUMMARY, recipe.getSummary());
        values.put(COLUMN_INSTRUCTIONS, recipe.getInstructions());
        values.put(COLUMN_READY_IN_MINUTES, recipe.getReadyInMinutes());
        values.put(COLUMN_SERVINGS, recipe.getServings());
        //TODO: Ingredients and Equipments

        db.insert(TABLE_RECIPES, null, values);
        db.close();
    }

    public void deleteRecipe(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())});
        db.close();
    }

    public RecipeInformation getRecipe(int recipeID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE, COLUMN_SUMMARY, COLUMN_INSTRUCTIONS, COLUMN_READY_IN_MINUTES, COLUMN_SERVINGS},
                COLUMN_ID + "=?", new String[]{String.valueOf(recipeID)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String image = cursor.getString(2);
            String summary = cursor.getString(3);
            String instructions = cursor.getString(4);
            int readyInMinutes = cursor.getInt(5);
            int servings = cursor.getInt(6);

            //TODO: Ingredients and Equipments
            RecipeInformation recipe = new RecipeInformation(id, title, image, summary, instructions, null, null, readyInMinutes, servings);
            return recipe;
        }

        return null;
    }


}
