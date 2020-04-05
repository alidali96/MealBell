package ca.mealbell.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mealbell.javabeans.Equipement;
import ca.mealbell.javabeans.Ingredient;
import ca.mealbell.javabeans.Measurements;
import ca.mealbell.javabeans.MeasurementsSet;
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

    private final String COLUMN_NAME = "name";

    private final String COLUMN_US_AMOUNT = "us_amount";
    private final String COLUMN_METRIC_AMOUNT = "metric_amount";
    private final String COLUMN_US_SHORT = "us_short";
    private final String COLUMN_METRIC_SHORT = "metric_short";


    // CREATE RECIPE TABLE
    private final String CREATE_RECIPES_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (" +
            "%s INTEGER PRIMARY KEY," +
            "%s VARCHAR(255)," +
            "%s VARCHAR(255)," +
            "%s TEXT," +
            "%s TEXT," +
            "%s INTEGER," +
            "%s INTEGER)", TABLE_RECIPES, COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE, COLUMN_SUMMARY, COLUMN_INSTRUCTIONS, COLUMN_READY_IN_MINUTES, COLUMN_SERVINGS);

    private final String CREATE_INGREDIENT_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (" +
            "%s INTEGER NOT NULL," +
            "%s VARCHAR(255)," +
            "%s VARCHAR(255)," +
            "%s DECIMAL," +
            "%s VARCHAR(50)," +
            "%s DECIMAL," +
            "%s VARCHAR(50)," +
            "FOREIGN KEY (%s) " +
            "REFERENCES %s (%s) )", TABLE_INGREDIENTS, COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_US_AMOUNT, COLUMN_US_SHORT, COLUMN_METRIC_AMOUNT, COLUMN_METRIC_SHORT, COLUMN_ID, TABLE_RECIPES, COLUMN_ID);


    // CREATE EQUIPMENT TABLE
    private final String CREATE_EQUIPMENT_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (" +
            "%s INTEGER NOT NULL," +
            "%s VARCHAR(255)," +
            "%s VARCHAR(255)," +
            "FOREIGN KEY (%s) " +
            "REFERENCES %s (%s) )", TABLE_EQUIPMENTS, COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_ID, TABLE_RECIPES, COLUMN_ID);


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
        db.execSQL(CREATE_INGREDIENT_TABLE);
        db.execSQL(CREATE_EQUIPMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENTS);

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

            // Add Ingredients and Equipments
            Equipement[] equipments = new Equipement[getAllEquipments(id).size()];
            equipments = getAllEquipments(id).toArray(equipments);

            Ingredient[] ingredients = new Ingredient[getAllIngredients(id).size()];
            ingredients = getAllIngredients(id).toArray(ingredients);

            RecipeInformation recipe = new RecipeInformation(id, title, image, summary, instructions, ingredients, equipments, readyInMinutes, servings);
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

        db.insert(TABLE_RECIPES, null, values);
        db.close();

        // Ingredients and Equipments
        addEquipments(recipe);
        addIngredients(recipe);
    }

    public void deleteRecipe(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())});
        db.close();

        // Ingredients and Equipments
        deleteEquipments(recipe);
        deleteIngredients(recipe);
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

            // Ingredients and Equipments
            Equipement[] equipments = new Equipement[getAllEquipments(recipeID).size()];
            equipments = getAllEquipments(recipeID).toArray(equipments);

            Ingredient[] ingredients = new Ingredient[getAllIngredients(id).size()];
            ingredients = getAllIngredients(id).toArray(ingredients);


            RecipeInformation recipe = new RecipeInformation(id, title, image, summary, instructions, ingredients, equipments, readyInMinutes, servings);
            return recipe;
        }

        return null;
    }


    private List<Equipement> getAllEquipments(int recipeID) {
        List<Equipement> equipements = new ArrayList<>();

        String query = String.format("SELECT  %s,%s FROM %s WHERE %s = %d", COLUMN_NAME, COLUMN_IMAGE, TABLE_EQUIPMENTS, COLUMN_ID, recipeID);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String image = cursor.getString(1);

            Equipement equipement = new Equipement(name, image);
            equipements.add(equipement);
        }
        return equipements;
    }

    private void addEquipments(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Equipement equipment :
                recipe.getEquipments()) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, recipe.getId());
            values.put(COLUMN_NAME, equipment.getName());
            values.put(COLUMN_IMAGE, equipment.getImage());

            db.insert(TABLE_EQUIPMENTS, null, values);
        }

        db.close();
    }

    private void deleteEquipments(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Equipement equipment :
                recipe.getEquipments()) {

            db.delete(TABLE_EQUIPMENTS, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(recipe.getId())});
        }

        db.close();
    }

    private List<Ingredient> getAllIngredients(int recipeID) {
        List<Ingredient> ingredients = new ArrayList<>();

        String query = String.format("SELECT  * FROM %s WHERE %s = %d", TABLE_INGREDIENTS, COLUMN_ID, recipeID);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            double usAmount = cursor.getDouble(3);
            String usShort = cursor.getString(4);
            double metricAmount = cursor.getDouble(5);
            String metricShort = cursor.getString(6);

            Ingredient ingredient = new Ingredient(name, image, new MeasurementsSet(new Measurements(usAmount, usShort), new Measurements(metricAmount, metricShort)));
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private void addIngredients(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Ingredient ingredient :
                recipe.getExtendedIngredients()) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, recipe.getId());
            values.put(COLUMN_NAME, ingredient.getName());
            values.put(COLUMN_IMAGE, ingredient.getImage());
            values.put(COLUMN_US_AMOUNT, ingredient.getMeasures().getUs().getAmount());
            values.put(COLUMN_US_SHORT, ingredient.getMeasures().getUs().getUnitShort());
            values.put(COLUMN_METRIC_AMOUNT, ingredient.getMeasures().getMetric().getAmount());
            values.put(COLUMN_METRIC_SHORT, ingredient.getMeasures().getMetric().getUnitShort());

            db.insert(TABLE_INGREDIENTS, null, values);
        }

        db.close();
    }

    private void deleteIngredients(RecipeInformation recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Ingredient ingredient :
                recipe.getExtendedIngredients()) {

            db.delete(TABLE_INGREDIENTS, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(recipe.getId())});
        }

        db.close();
    }

}
