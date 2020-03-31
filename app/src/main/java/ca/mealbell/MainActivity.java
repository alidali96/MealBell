package ca.mealbell;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    public static final Map<String, String> FOOD_API_HEADERS = new HashMap<>();

    // Access FAB in other fragments
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // hide fab
//        fab.hide();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_serach_recipes, R.id.nav_meal_planner)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // API Headers
        FOOD_API_HEADERS.put("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
        FOOD_API_HEADERS.put("x-rapidapi-key", getString(R.string.api_key));

        // Set Trivia Notification
        NotificationsManager triviaNotification = new NotificationsManager.Builder(this)
                .setChannelID("trivia")
                .setChannelName("Food Trivia")
                .setChannelDescription("This will show random food trivia")
                .setNotificationType(NotificationsManager.FOOD_TRIVIA)
                .setNotificationTime(new NotificationTime(20))
                .setRepeat(NotificationsManager.Repeat.INTERVAL_DAY)
                .build();
        triviaNotification.launchNotification();

        // Set Jokes Notification
        NotificationsManager jokesNotification = new NotificationsManager.Builder(this)
                .setChannelID("jokes")
                .setChannelName("Food Jokes")
                .setChannelDescription("This will show random food jokes")
                .setNotificationType(NotificationsManager.FOOD_JOKE)
                .setNotificationTime(new NotificationTime(8))
                .setRepeat(NotificationsManager.Repeat.INTERVAL_DAY)
                .build();
        jokesNotification.launchNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
