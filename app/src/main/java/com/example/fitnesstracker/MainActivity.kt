package com.example.fitnesstracker

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import android.media.MediaPlayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.VisualTransformation
import coil.compose.AsyncImage
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.fitnesstracker.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Fitness App Themes - Energetic and motivating colors
sealed class FitnessTheme(
    val name: String,
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val accent: Color
) {
    object Energetic: FitnessTheme(
        "Energetic Orange",
        Color(0xFFFF6B35),
        Color(0xFFFFB347),
        Color(0xFFFFF8F0),
        Color(0xFFFFFFFF),
        Color(0xFF2D2D2D),
        Color(0xFFFF4500)
    )

    object Strong: FitnessTheme(
        "Strong Blue",
        Color(0xFF1E88E5),
        Color(0xFF64B5F6),
        Color(0xFFF3F8FF),
        Color(0xFFFFFFFF),
        Color(0xFF1565C0),
        Color(0xFF2196F3)
    )

    object Vibrant: FitnessTheme(
        "Vibrant Green",
        Color(0xFF43A047),
        Color(0xFF81C784),
        Color(0xFFF1F8E9),
        Color(0xFFFFFFFF),
        Color(0xFF2E7D32),
        Color(0xFF4CAF50)
    )

    object Power: FitnessTheme(
        "Power Purple",
        Color(0xFF8E24AA),
        Color(0xFFBA68C8),
        Color(0xFFF3E5F5),
        Color(0xFFFFFFFF),
        Color(0xFF6A1B9A),
        Color(0xFF9C27B0)
    )
}
object FitnessStrings {
    private val translations = mapOf(
        "en" to mapOf(
            // Login/Signup
            "app_name" to "FitTracker Pro",
            "sign_in_subtitle" to "Sign in to track your fitness journey",
            "welcome_back" to "Welcome Back! 🔥",
            "email" to "Email",
            "password" to "Password",
            "sign_in" to "Sign In 🚀",
            "new_to_fitness" to "New to fitness? ",
            "start_journey" to "Start Journey 🌟",
            "join_fittracker" to "Join FitTracker",
            "start_transformation" to "Start your fitness transformation today",
            "lets_get_started" to "Let's Get Started! 💪",
            "full_name" to "Full Name",
            "confirm_password" to "Confirm Password",
            "already_member" to "Already a member? ",
            "sign_in_simple" to "Sign In 🏃",

            // Main Navigation
            "workouts" to "Workouts",
            "routines" to "Routines",
            "progress" to "Progress",
            "profile" to "Profile",

            // Workouts Screen
            "my_workouts" to "My Workouts 💪",
            "exercises_ready" to "exercises ready to go",
            "search_exercises" to "Search exercises, difficulty...",
            "total" to "Total",
            "favorites" to "Favorites",
            "categories" to "Categories",
            "all" to "All",
            "easy" to "Easy",
            "medium" to "Medium",
            "hard" to "Hard",
            "no_exercises_found" to "No exercises found",
            "ready_to_start" to "Ready to start?",
            "add_first_exercise" to "Add First Exercise",
            "start_workout" to "Start Workout ▶️",
            "add_new_exercise" to "Add New Exercise 💪",
            "exercise_name" to "Exercise Name",
            "category" to "Category",
            "duration" to "Duration",
            "calories" to "Calories",
            "difficulty" to "Difficulty",
            "cancel" to "Cancel",
            "add_exercise" to "Add Exercise",
            "about_exercise" to "About this Exercise",
            "close" to "Close",

            // Routines Screen
            "my_routines" to "My Routines 📋",
            "routines_ready" to "routines ready",
            "search_routines" to "Search routines, types...",
            "this_week" to "This Week",
            "avg_duration" to "Avg Duration",
            "recent" to "Recent",
            "full_body" to "Full Body",
            "strength" to "Strength",
            "cardio" to "Cardio",
            "hiit" to "HIIT",
            "recovery" to "Recovery",
            "no_routines_found" to "No routines found",
            "create_first_routine" to "Create your first routine",
            "create_routine" to "Create Routine 📋",
            "routine_name" to "Routine Name",
            "routine_type" to "Routine Type",
            "select_exercises" to "Select Exercises",
            "create_routine_button" to "Create Routine",
            "start_routine" to "Start Routine ▶️",
            "exercises_in_routine" to "Exercises in this Routine",

            // Progress Screen
            "my_progress" to "My Progress 📊",
            "track_journey" to "Track your fitness journey",
            "week" to "Week",
            "month" to "Month",
            "year" to "Year",
            "workouts_count" to "workouts",
            "total_time" to "Total Time",
            "hours" to "hours",
            "streak" to "Streak",
            "days" to "days",
            "personal_best" to "Personal best!",
            "weekly_activity" to "Weekly Activity 📈",
            "minutes" to "Minutes",
            "average" to "Average",
            "monthly_goals" to "Monthly Goals 🎯",
            "completed" to "completed",
            "workout_days" to "Workout Days",
            "calories_burned" to "Calories Burned",
            "weight_goal" to "Weight Goal",
            "achievements" to "Achievements 🏆",
            "detailed_stats" to "Detailed Stats 📊",
            "total_workouts" to "Total Workouts",
            "total_hours" to "Total Hours",
            "average_session" to "Average Session",
            "longest_streak" to "Longest Streak",
            "favorite_exercise" to "Favorite Exercise",
            "most_active_day" to "Most Active Day",

            // Profile Screen
            "my_profile" to "My Profile 👤",
            "edit_profile" to "Edit Profile ✏️",
            "settings" to "Settings ⚙️",
            "theme" to "Theme",
            "language" to "Language",
            "notifications" to "Notifications",
            "enabled" to "Enabled",
            "disabled" to "Disabled",
            "dark_mode" to "Dark Mode",
            "dark_theme" to "Dark theme",
            "light_theme" to "Light theme",
            "advanced_settings" to "Advanced Settings",
            "backup_sync_more" to "Backup, sync, and more",
            "sign_out" to "Sign Out",
            "tap_change_photo" to "Tap to change photo",
            "save_changes" to "Save Changes",
            "choose_theme" to "Choose Theme 🎨",
            "select_language" to "Select Language 🌍",
            "sync_devices" to "Sync Devices",
            "sync_subtitle" to "Sync across all your devices",
            "health_integration" to "Health App Integration",
            "health_subtitle" to "Connect with Apple Health/Google Fit",
            "workout_reminders" to "Workout Reminders",
            "reminders_subtitle" to "Set custom workout reminders",
            "analytics" to "Analytics",
            "analytics_subtitle" to "Detailed workout analytics",
            "privacy" to "Privacy",
            "privacy_subtitle" to "Manage your privacy settings",
            "clear_data" to "Clear Data",
            "clear_data_subtitle" to "Reset all workout data",
            "terms_conditions" to "Terms & Conditions",
            "terms_subtitle" to "Read our terms and conditions",
            "privacy_policy" to "Privacy Policy",
            "policy_subtitle" to "Read our privacy policy",
            "about" to "About",
            "about_subtitle" to "App information and credits",

            // Common
            "min" to "min",
            "cal" to "cal",
            "today" to "Today",
            "yesterday" to "Yesterday",
            "tomorrow" to "Tomorrow"
        ),

        "ur" to mapOf(
            // Login/Signup - Urdu
            "app_name" to "فٹ ٹریکر پرو",
            "sign_in_subtitle" to "اپنے فٹنس سفر کو ٹریک کرنے کے لیے سائن ان کریں",
            "welcome_back" to "واپس خوش آمدید! 🔥",
            "email" to "ای میل",
            "password" to "پاس ورڈ",
            "sign_in" to "سائن ان 🚀",
            "new_to_fitness" to "فٹنس میں نئے ہیں؟ ",
            "start_journey" to "سفر شروع کریں 🌟",
            "join_fittracker" to "فٹ ٹریکر میں شامل ہوں",
            "start_transformation" to "آج اپنی فٹنس تبدیلی شروع کریں",
            "lets_get_started" to "آئیے شروع کرتے ہیں! 💪",
            "full_name" to "پورا نام",
            "confirm_password" to "پاس ورڈ کی تصدیق کریں",
            "already_member" to "پہلے سے رکن ہیں؟ ",
            "sign_in_simple" to "سائن ان 🏃",

            // Main Navigation - Urdu
            "workouts" to "ورک آؤٹس",
            "routines" to "روٹینز",
            "progress" to "پیش قدمی",
            "profile" to "پروفائل",

            // Workouts Screen - Urdu
            "my_workouts" to "میرے ورک آؤٹس 💪",
            "exercises_ready" to "ورزشیں تیار ہیں",
            "search_exercises" to "ورزشیں، مشکل تلاش کریں...",
            "total" to "کل",
            "favorites" to "پسندیدہ",
            "categories" to "زمرے",
            "all" to "تمام",
            "easy" to "آسان",
            "medium" to "درمیانہ",
            "hard" to "مشکل",
            "no_exercises_found" to "کوئی ورزش نہیں ملی",
            "ready_to_start" to "شروع کرنے کے لیے تیار؟",
            "add_first_exercise" to "پہلی ورزش شامل کریں",
            "start_workout" to "ورک آؤٹ شروع کریں ▶️",
            "add_new_exercise" to "نئی ورزش شامل کریں 💪",
            "exercise_name" to "ورزش کا نام",
            "category" to "زمرہ",
            "duration" to "مدت",
            "calories" to "کیلوریز",
            "difficulty" to "مشکل",
            "cancel" to "منسوخ کریں",
            "add_exercise" to "ورزش شامل کریں",
            "about_exercise" to "اس ورزش کے بارے میں",
            "close" to "بند کریں",

            // Add more Urdu translations...
            "my_routines" to "میرے روٹینز 📋",
            "my_progress" to "میری پیش قدمی 📊",
            "my_profile" to "میرا پروفائل 👤"
        ),

        "hi" to mapOf(
            // Login/Signup - Hindi
            "app_name" to "फिट ट्रैकर प्रो",
            "sign_in_subtitle" to "अपनी फिटनेस यात्रा को ट्रैक करने के लिए साइन इन करें",
            "welcome_back" to "वापस स्वागत है! 🔥",
            "email" to "ईमेल",
            "password" to "पासवर्ड",
            "sign_in" to "साइन इन 🚀",
            "new_to_fitness" to "फिटनेस में नए हैं? ",
            "start_journey" to "यात्रा शुरू करें 🌟",
            "join_fittracker" to "फिट ट्रैकर में शामिल हों",
            "start_transformation" to "आज अपना फिटनेस परिवर्तन शुरू करें",
            "lets_get_started" to "चलिए शुरू करते हैं! 💪",
            "full_name" to "पूरा नाम",
            "confirm_password" to "पासवर्ड की पुष्टि करें",
            "already_member" to "पहले से सदस्य हैं? ",
            "sign_in_simple" to "साइन इन 🏃",

            // Main Navigation - Hindi
            "workouts" to "वर्कआउट्स",
            "routines" to "रूटीन्स",
            "progress" to "प्रगति",
            "profile" to "प्रोफाइल",

            // Key screens - Hindi
            "my_workouts" to "मेरे वर्कआउट्स 💪",
            "my_routines" to "मेरे रूटीन्स 📋",
            "my_progress" to "मेरी प्रगति 📊",
            "my_profile" to "मेरा प्रोफाइल 👤"
        ),

        "zh" to mapOf(
            // Login/Signup - Chinese
            "app_name" to "健身追踪专业版",
            "sign_in_subtitle" to "登录以追踪您的健身之旅",
            "welcome_back" to "欢迎回来！🔥",
            "email" to "邮箱",
            "password" to "密码",
            "sign_in" to "登录 🚀",
            "new_to_fitness" to "健身新手？",
            "start_journey" to "开始旅程 🌟",
            "join_fittracker" to "加入健身追踪",
            "start_transformation" to "今天开始您的健身转变",
            "lets_get_started" to "让我们开始吧！💪",
            "full_name" to "全名",
            "confirm_password" to "确认密码",
            "already_member" to "已经是会员？",
            "sign_in_simple" to "登录 🏃",

            // Main Navigation - Chinese
            "workouts" to "锻炼",
            "routines" to "例程",
            "progress" to "进度",
            "profile" to "个人资料",

            // Key screens - Chinese
            "my_workouts" to "我的锻炼 💪",
            "my_routines" to "我的例程 📋",
            "my_progress" to "我的进度 📊",
            "my_profile" to "我的个人资料 👤"
        ),

        "es" to mapOf(
            // Login/Signup - Spanish
            "app_name" to "FitTracker Pro",
            "sign_in_subtitle" to "Inicia sesión para seguir tu viaje fitness",
            "welcome_back" to "¡Bienvenido de vuelta! 🔥",
            "email" to "Correo",
            "password" to "Contraseña",
            "sign_in" to "Iniciar Sesión 🚀",
            "new_to_fitness" to "¿Nuevo en fitness? ",
            "start_journey" to "Comenzar Viaje 🌟",
            "join_fittracker" to "Únete a FitTracker",
            "start_transformation" to "Comienza tu transformación fitness hoy",
            "lets_get_started" to "¡Empecemos! 💪",
            "full_name" to "Nombre Completo",
            "confirm_password" to "Confirmar Contraseña",
            "already_member" to "¿Ya eres miembro? ",
            "sign_in_simple" to "Iniciar Sesión 🏃",

            // Main Navigation - Spanish
            "workouts" to "Entrenamientos",
            "routines" to "Rutinas",
            "progress" to "Progreso",
            "profile" to "Perfil",

            // Key screens - Spanish
            "my_workouts" to "Mis Entrenamientos 💪",
            "my_routines" to "Mis Rutinas 📋",
            "my_progress" to "Mi Progreso 📊",
            "my_profile" to "Mi Perfil 👤"
        )
    )

    fun getString(key: String, language: String = "en"): String {
        return translations[language]?.get(key) ?: translations["en"]?.get(key) ?: key
    }
}
// Workout data class
data class WorkoutExercise(
    val id: String,
    val name: String,
    val category: ExerciseCategory,
    val imageUrl: String,
    val duration: String = "",
    val difficulty: String = "",
    val calories: String = "",
    val isFavorite: Boolean = false
)

// Workout routine data class
data class WorkoutRoutine(
    val id: String,
    val name: String,
    val exercises: List<WorkoutExercise>,
    val type: String = "",
    val date: String = "",
    val isFavorite: Boolean = false,
    val imageUrl: String = "",
    val duration: String = "",
    val calories: String = ""
)

// Exercise categories with emojis
enum class ExerciseCategory(val displayName: String, val emoji: String) {
    CARDIO("Cardio", "🏃"),
    STRENGTH("Strength", "💪"),
    YOGA("Yoga", "🧘"),
    FLEXIBILITY("Flexibility", "🤸"),
    HIIT("HIIT", "⚡"),
    SPORTS("Sports", "⚽")
}

// Fitness images from Unsplash
object FitnessImages {
    val cardioExercises = listOf(
        "https://images.unsplash.com/photo-1571019613914-85f342c6a11e?w=400&h=300&fit=crop", // Running
        "https://images.unsplash.com/photo-1538805060514-97d9cc17730c?w=400&h=300&fit=crop", // Cycling
        "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=300&fit=crop", // Jumping
        "https://images.unsplash.com/photo-1504609813442-a8924e83f76e?w=400&h=300&fit=crop"  // Dancing
    )

    val strengthExercises = listOf(
        "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400&h=300&fit=crop", // Weightlifting
        "https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=400&h=300&fit=crop", // Dumbbells
        "https://images.unsplash.com/photo-1517963879433-6ad2b056d712?w=400&h=300&fit=crop", // Push-ups
        "https://images.unsplash.com/photo-1605296867304-46d5465a13f1?w=400&h=300&fit=crop"  // Barbell
    )

    val yogaExercises = listOf(
        "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop", // Yoga pose
        "https://images.unsplash.com/photo-1588286840104-8957b019727f?w=400&h=300&fit=crop", // Meditation
        "https://images.unsplash.com/photo-1599901860904-17e6ed7083a0?w=400&h=300&fit=crop", // Stretching
        "https://images.unsplash.com/photo-1545205597-3d9d02c29597?w=400&h=300&fit=crop"   // Balance
    )

    val hiitExercises = listOf(
        "https://images.unsplash.com/photo-1571019613914-85f342c6a11e?w=400&h=300&fit=crop", // High intensity
        "https://images.unsplash.com/photo-1605296867304-46d5465a13f1?w=400&h=300&fit=crop", // Circuit training
        "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=300&fit=crop", // Plyometrics
        "https://images.unsplash.com/photo-1517963879433-6ad2b056d712?w=400&h=300&fit=crop"  // Burpees
    )

    val workoutRoutines = listOf(
        "https://images.unsplash.com/photo-1571019613914-85f342c6a11e?w=400&h=500&fit=crop", // Full body
        "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?w=400&h=500&fit=crop", // Gym session
        "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=500&fit=crop", // Yoga flow
        "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=500&fit=crop"   // HIIT session
    )
}

// Sample fitness data
object SampleFitnessData {
    val sampleExercises = listOf(
        WorkoutExercise("1", "Morning Run", ExerciseCategory.CARDIO, FitnessImages.cardioExercises[0], "30 min", "Medium", "300 cal"),
        WorkoutExercise("2", "Push-ups", ExerciseCategory.STRENGTH, FitnessImages.strengthExercises[2], "15 min", "Easy", "150 cal"),
        WorkoutExercise("3", "Yoga Flow", ExerciseCategory.YOGA, FitnessImages.yogaExercises[0], "45 min", "Easy", "200 cal"),
        WorkoutExercise("4", "HIIT Circuit", ExerciseCategory.HIIT, FitnessImages.hiitExercises[0], "20 min", "Hard", "400 cal"),
        WorkoutExercise("5", "Weight Training", ExerciseCategory.STRENGTH, FitnessImages.strengthExercises[0], "60 min", "Hard", "350 cal"),
        WorkoutExercise("6", "Stretching", ExerciseCategory.FLEXIBILITY, FitnessImages.yogaExercises[2], "20 min", "Easy", "100 cal")
    )

    val sampleRoutines = listOf(
        WorkoutRoutine("1", "Morning Energy", sampleExercises.take(3), "Full Body", "Today", false, FitnessImages.workoutRoutines[0], "90 min", "650 cal"),
        WorkoutRoutine("2", "Strength Builder", listOf(sampleExercises[1], sampleExercises[4]), "Strength", "Yesterday", true, FitnessImages.workoutRoutines[1], "75 min", "500 cal"),
        WorkoutRoutine("3", "Recovery Day", listOf(sampleExercises[2], sampleExercises[5]), "Recovery", "Tomorrow", false, FitnessImages.workoutRoutines[2], "65 min", "300 cal")
    )
}

// Language data
data class Language(val name: String, val code: String)

val availableLanguages = listOf(
    Language("English", "en"),
    Language("اردو", "ur"),
    Language("हिंदी", "hi"),
    Language("中文", "zh"),
    Language("Español", "es")
)

// Navigation destinations with emojis
sealed class Screen(val route: String, val title: String, val emoji: String) {
    object Workouts : Screen("workouts", "Workouts", "🏋️")
    object Routines : Screen("routines", "Routines", "📋")
    object Progress : Screen("progress", "Progress", "📊")
    object Profile : Screen("profile", "Profile", "👤")
}

// DataStore setup
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fitness_preferences")

object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val SELECTED_THEME = stringPreferencesKey("selected_theme")
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
    val PROFILE_PICTURE_PATH = stringPreferencesKey("profile_picture_path")
    val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
    val USER_ID = stringPreferencesKey("user_id")
    val STORED_PASSWORD = stringPreferencesKey("stored_password")
}

// User preferences data class
data class UserPreferences(
    val userName: String,
    val userEmail: String,
    val selectedTheme: String,
    val selectedLanguage: String,
    val notificationsEnabled: Boolean,
    val darkModeEnabled: Boolean,
    val profilePicturePath: String,
    val isAuthenticated: Boolean = false,
    val userId: String = "",
    val storedPassword: String = ""
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String = ""
)

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen("splash")
    object Login : AuthScreen("login")
    object Signup : AuthScreen("signup")
    object Main : AuthScreen("main")
}

data class AuthState(
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class ViewMode { GRID, LIST }

@Composable
fun LocalizedText(
    key: String,
    language: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    FitnessText(
        text = FitnessStrings.getString(key, language),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style
    )
}




// Custom composables for fitness app
@Composable
fun FitnessText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = {},
        style = style
    )
}

@Composable
fun FitnessButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
}

// User Preferences repository
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
        }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = email
        }
    }

    suspend fun saveSelectedTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_THEME] = theme
        }
    }

    suspend fun saveSelectedLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_LANGUAGE] = language
        }
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun saveDarkModeEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE_ENABLED] = enabled
        }
    }

    suspend fun saveProfilePicturePath(path: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PROFILE_PICTURE_PATH] = path
        }
    }

    suspend fun saveAuthState(isAuthenticated: Boolean, userId: String = "", password: String = "") {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_AUTHENTICATED] = isAuthenticated
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.STORED_PASSWORD] = password
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_AUTHENTICATED] = false
            preferences[PreferencesKeys.USER_ID] = ""
            preferences[PreferencesKeys.STORED_PASSWORD] = ""
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            userName = preferences[PreferencesKeys.USER_NAME] ?: "",
            userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "",
            selectedTheme = preferences[PreferencesKeys.SELECTED_THEME] ?: "Energetic Orange",
            selectedLanguage = preferences[PreferencesKeys.SELECTED_LANGUAGE] ?: "en",
            notificationsEnabled = preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true,
            darkModeEnabled = preferences[PreferencesKeys.DARK_MODE_ENABLED] ?: false,
            profilePicturePath = preferences[PreferencesKeys.PROFILE_PICTURE_PATH] ?: "",
            isAuthenticated = preferences[PreferencesKeys.IS_AUTHENTICATED] ?: false,
            userId = preferences[PreferencesKeys.USER_ID] ?: "",
            storedPassword = preferences[PreferencesKeys.STORED_PASSWORD] ?: ""
        )
    }
}

// ViewModel
class UserPreferencesViewModel(private val repository: UserPreferencesRepository) : ViewModel() {
    val userPreferences = repository.userPreferencesFlow

    suspend fun saveUserName(name: String) {
        repository.saveUserName(name)
    }

    suspend fun saveUserEmail(email: String) {
        repository.saveUserEmail(email)
    }

    suspend fun saveSelectedTheme(theme: String) {
        repository.saveSelectedTheme(theme)
    }

    suspend fun saveSelectedLanguage(language: String) {
        repository.saveSelectedLanguage(language)
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        repository.saveNotificationsEnabled(enabled)
    }

    suspend fun saveDarkModeEnabled(enabled: Boolean) {
        repository.saveDarkModeEnabled(enabled)
    }

    suspend fun saveProfilePicturePath(path: String) {
        repository.saveProfilePicturePath(path)
    }

    suspend fun login(email: String, password: String): Boolean {
        if (email.isNotEmpty() && password.length >= 6) {
            val userId = System.currentTimeMillis().toString()
            repository.saveAuthState(true, userId, password)
            repository.saveUserEmail(email)
            return true
        }
        return false
    }

    suspend fun signup(name: String, email: String, password: String): Boolean {
        if (name.isNotEmpty() && email.isNotEmpty() && password.length >= 6) {
            val userId = System.currentTimeMillis().toString()
            repository.saveAuthState(true, userId, password)
            repository.saveUserName(name)
            repository.saveUserEmail(email)
            return true
        }
        return false
    }

    suspend fun logout() {
        repository.logout()
    }
}

class UserPreferencesViewModelFactory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserPreferencesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SplashScreen_Custom)
        super.onCreate(savedInstanceState)
        initializeBackgroundMusic()
        Handler(Looper.getMainLooper()).postDelayed({
            setTheme(R.style.Theme_ObjectComboGenerator)
            initializeContent()
        }, 2500)
    }

    private fun initializeBackgroundMusic() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.your_background_music)
            mediaPlayer?.apply {
                isLooping = true
                setVolume(0.3f, 0.3f)
                prepareAsync()
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.start()
                }
                setOnErrorListener { _, what, extra ->
                    false
                }
            }
        } catch (e: Exception) {
            // Handle error
        }
    }

    private fun initializeContent() {
        setContent {
            val context = LocalContext.current
            val repository = UserPreferencesRepository(context.dataStore)
            val viewModel: UserPreferencesViewModel = viewModel(
                factory = UserPreferencesViewModelFactory(repository)
            )

            val userPreferences by viewModel.userPreferences.collectAsState(
                initial = UserPreferences(
                    userName = "",
                    userEmail = "",
                    selectedTheme = "Energetic Orange",
                    selectedLanguage = "en",
                    notificationsEnabled = true,
                    darkModeEnabled = false,
                    profilePicturePath = "",
                    isAuthenticated = false,
                    userId = "",
                    storedPassword = ""
                )
            )

            val currentTheme = when (userPreferences.selectedTheme) {
                "Strong Blue" -> FitnessTheme.Strong
                "Vibrant Green" -> FitnessTheme.Vibrant
                "Power Purple" -> FitnessTheme.Power
                else -> FitnessTheme.Energetic
            }

            val darkMode = userPreferences.darkModeEnabled

            val colorScheme = when {
                darkMode -> darkColorScheme(
                    primary = currentTheme.primary,
                    secondary = currentTheme.secondary,
                    background = Color(0xFF121212),
                    surface = Color(0xFF1E1E1E),
                    onSurface = Color.White
                )
                else -> lightColorScheme(
                    primary = currentTheme.primary,
                    secondary = currentTheme.secondary,
                    background = currentTheme.background,
                    surface = currentTheme.surface,
                    onSurface = currentTheme.onSurface
                )
            }

            MaterialTheme(
                colorScheme = colorScheme
            ) {
                AuthenticationApp(viewModel, userPreferences, currentTheme)
            }
        }
    }
}

@Composable
fun AuthenticationApp(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (userPreferences.isAuthenticated) AuthScreen.Main.route else AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            FitnessLoginScreen(
                onLoginSuccess = {
                    navController.navigate(AuthScreen.Main.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(AuthScreen.Signup.route)
                },
                viewModel = viewModel,
                currentTheme = currentTheme
            )
        }

        composable(AuthScreen.Signup.route) {
            FitnessSignupScreen(
                onSignupSuccess = {
                    navController.navigate(AuthScreen.Main.route) {
                        popUpTo(AuthScreen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                viewModel = viewModel,
                currentTheme = currentTheme
            )
        }

        composable(AuthScreen.Main.route) {
            FitnessTrackerApp(
                viewModel = viewModel,
                userPreferences = userPreferences,
                currentTheme = currentTheme,
                onLogout = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessLoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: UserPreferencesViewModel,
    currentTheme: FitnessTheme
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    // Get current language from user preferences
    val userPreferences by viewModel.userPreferences.collectAsState(
        initial = UserPreferences(
            userName = "",
            userEmail = "",
            selectedTheme = "Energetic Orange",
            selectedLanguage = "en",
            notificationsEnabled = true,
            darkModeEnabled = false,
            profilePicturePath = "",
            isAuthenticated = false,
            userId = "",
            storedPassword = ""
        )
    )
    val currentLanguage = userPreferences.selectedLanguage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        currentTheme.primary.copy(alpha = 0.1f),
                        currentTheme.accent.copy(alpha = 0.05f)
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title Section with fitness emoji
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            colors = listOf(currentTheme.primary, currentTheme.accent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                FitnessText(
                    text = "💪",
                    fontSize = 60.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LocalizedText(
                key = "app_name",
                language = currentLanguage,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = currentTheme.primary
            )

            LocalizedText(
                key = "sign_in_subtitle",
                language = currentLanguage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Login Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    LocalizedText(
                        key = "welcome_back",
                        language = currentLanguage,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = ""
                        },
                        label = { LocalizedText(key = "email", language = currentLanguage) },
                        leadingIcon = {
                            FitnessText(
                                text = "📧",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        label = { LocalizedText(key = "password", language = currentLanguage) },
                        leadingIcon = {
                            FitnessText(
                                text = "🔒",
                                fontSize = 20.sp
                            )
                        },
                        trailingIcon = {
                            FitnessText(
                                text = if (passwordVisible) "🙈" else "👁️",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        FitnessText(
                            text = errorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    FitnessButton(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Please fill in all fields"
                                return@FitnessButton
                            }

                            isLoading = true
                            coroutineScope.launch {
                                val success = viewModel.login(email, password)
                                isLoading = false
                                if (success) {
                                    onLoginSuccess()
                                } else {
                                    errorMessage = "Invalid email or password"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = currentTheme.accent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            LocalizedText(
                                key = "sign_in",
                                language = currentLanguage,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LocalizedText(
                            key = "new_to_fitness",
                            language = currentLanguage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        LocalizedText(
                            key = "start_journey",
                            language = currentLanguage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = currentTheme.accent,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { onNavigateToSignup() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessSignupScreen(
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: UserPreferencesViewModel,
    currentTheme: FitnessTheme
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        currentTheme.primary.copy(alpha = 0.1f),
                        currentTheme.accent.copy(alpha = 0.05f)
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title Section
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            colors = listOf(currentTheme.primary, currentTheme.accent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                FitnessText(
                    text = "🏃",
                    fontSize = 50.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FitnessText(
                text = "Join FitTracker",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = currentTheme.primary
            )

            FitnessText(
                text = "Start your fitness transformation today",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Signup Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    FitnessText(
                        text = "Let's Get Started! 💪",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            errorMessage = ""
                        },
                        label = { FitnessText("Full Name") },
                        leadingIcon = {
                            FitnessText(
                                text = "👤",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = ""
                        },
                        label = { FitnessText("Email") },
                        leadingIcon = {
                            FitnessText(
                                text = "📧",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        label = { FitnessText("Password") },
                        leadingIcon = {
                            FitnessText(
                                text = "🔒",
                                fontSize = 20.sp
                            )
                        },
                        trailingIcon = {
                            FitnessText(
                                text = if (passwordVisible) "🙈" else "👁️",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password Field
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            errorMessage = ""
                        },
                        label = { FitnessText("Confirm Password") },
                        leadingIcon = {
                            FitnessText(
                                text = "🔒",
                                fontSize = 20.sp
                            )
                        },
                        trailingIcon = {
                            FitnessText(
                                text = if (confirmPasswordVisible) "🙈" else "👁️",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { confirmPasswordVisible = !confirmPasswordVisible }
                            )
                        },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        FitnessText(
                            text = errorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Signup Button
                    FitnessButton(
                        onClick = {
                            when {
                                name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                    errorMessage = "Please fill in all fields"
                                }
                                password != confirmPassword -> {
                                    errorMessage = "Passwords do not match"
                                }
                                password.length < 6 -> {
                                    errorMessage = "Password must be at least 6 characters"
                                }
                                else -> {
                                    isLoading = true
                                    coroutineScope.launch {
                                        val success = viewModel.signup(name, email, password)
                                        isLoading = false
                                        if (success) {
                                            onSignupSuccess()
                                        } else {
                                            errorMessage = "Failed to create account"
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = currentTheme.accent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            FitnessText(
                                "Start Journey 🚀",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login Navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Already a member? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        FitnessText(
                            text = "Sign In 🏃",
                            style = MaterialTheme.typography.bodyMedium,
                            color = currentTheme.accent,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { onNavigateToLogin() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessTrackerApp(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme,
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    val currentLanguage = userPreferences.selectedLanguage

    val screens = listOf(
        Screen.Workouts,
        Screen.Routines,
        Screen.Progress,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    val interactionSource = remember { MutableInteractionSource() }

                    NavigationBarItem(
                        icon = {
                            FitnessText(
                                text = screen.emoji,
                                fontSize = 24.sp
                            )
                        },
                        label = {
                            LocalizedText(
                                key = screen.title.lowercase(),
                                language = currentLanguage,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 12.sp
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = currentTheme.accent,
                            selectedTextColor = currentTheme.accent,
                            indicatorColor = currentTheme.accent.copy(alpha = 0.2f)
                        ),
                        interactionSource = interactionSource
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Workouts.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Workouts.route) {
                WorkoutsScreen(viewModel, userPreferences, currentTheme)
            }
            composable(Screen.Routines.route) {
                RoutinesScreen(viewModel, userPreferences, currentTheme)
            }
            composable(Screen.Progress.route) {
                ProgressScreen(viewModel, userPreferences, currentTheme)
            }
            composable(Screen.Profile.route) {
                FitnessProfileScreen(viewModel, userPreferences, currentTheme, onLogout)
            }
        }
    }
}

// Helper Composables
@Composable
fun QuickStatCard(
    title: String,
    value: String,
    emoji: String,
    color: Color
) {
    Card(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FitnessText(
                text = emoji,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            FitnessText(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProfileInfoRow(
    emoji: String,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        FitnessText(
            text = emoji,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            FitnessText(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            FitnessText(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SettingsItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    currentTheme: FitnessTheme
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FitnessText(
            text = emoji,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            FitnessText(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        FitnessText(
            text = "➡️",
            fontSize = 18.sp
        )
    }
}

@Composable
fun FitnessStat(value: String, label: String, emoji: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FitnessText(
            text = emoji,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        FitnessText(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        FitnessText(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme
) {
    var selectedCategory by remember { mutableStateOf<ExerciseCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }
    var viewMode by remember { mutableStateOf(ViewMode.GRID) }
    var selectedExercise by remember { mutableStateOf<WorkoutExercise?>(null) }
    var favoriteExercises by remember { mutableStateOf(setOf<String>()) }

    // Enhanced sample data
    val enhancedExercises = remember {
        mutableStateListOf<WorkoutExercise>().apply {
            addAll(listOf(
                WorkoutExercise("1", "Morning Run", ExerciseCategory.CARDIO, FitnessImages.cardioExercises[0], "30 min", "Medium", "300 cal", true),
                WorkoutExercise("2", "Push-ups", ExerciseCategory.STRENGTH, FitnessImages.strengthExercises[2], "15 min", "Easy", "150 cal"),
                WorkoutExercise("3", "Yoga Flow", ExerciseCategory.YOGA, FitnessImages.yogaExercises[0], "45 min", "Easy", "200 cal"),
                WorkoutExercise("4", "HIIT Circuit", ExerciseCategory.HIIT, FitnessImages.hiitExercises[0], "20 min", "Hard", "400 cal", true),
                WorkoutExercise("5", "Weight Training", ExerciseCategory.STRENGTH, FitnessImages.strengthExercises[0], "60 min", "Hard", "350 cal"),
                WorkoutExercise("6", "Stretching", ExerciseCategory.FLEXIBILITY, FitnessImages.yogaExercises[2], "20 min", "Easy", "100 cal"),
                WorkoutExercise("7", "Cycling", ExerciseCategory.CARDIO, FitnessImages.cardioExercises[1], "45 min", "Medium", "400 cal"),
                WorkoutExercise("8", "Plank Challenge", ExerciseCategory.STRENGTH, FitnessImages.strengthExercises[3], "10 min", "Medium", "80 cal"),
                WorkoutExercise("9", "Dance Workout", ExerciseCategory.CARDIO, FitnessImages.cardioExercises[3], "40 min", "Easy", "350 cal", true),
                WorkoutExercise("10", "Power Yoga", ExerciseCategory.YOGA, FitnessImages.yogaExercises[1], "60 min", "Hard", "250 cal")
            ))
        }
    }

    // Filter exercises
    val filteredExercises = enhancedExercises.filter { exercise ->
        val matchesSearch = exercise.name.contains(searchQuery, ignoreCase = true) ||
                exercise.difficulty.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == null || exercise.category == selectedCategory
        val matchesFilter = when (selectedFilter) {
            "Favorites" -> exercise.isFavorite || favoriteExercises.contains(exercise.id)
            "Easy" -> exercise.difficulty == "Easy"
            "Medium" -> exercise.difficulty == "Medium"
            "Hard" -> exercise.difficulty == "Hard"
            "All" -> true
            else -> true
        }
        matchesSearch && matchesCategory && matchesFilter
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Column {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            FitnessText(
                                text = "My Workouts 💪",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            FitnessText(
                                text = "${filteredExercises.size} exercises ready to go",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Row {
                            // View mode toggle
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(currentTheme.accent.copy(alpha = 0.1f))
                                    .clickable { viewMode = if (viewMode == ViewMode.GRID) ViewMode.LIST else ViewMode.GRID },
                                contentAlignment = Alignment.Center
                            ) {
                                FitnessText(
                                    text = if (viewMode == ViewMode.GRID) "📋" else "⚏",
                                    fontSize = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Add exercise button
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(currentTheme.accent)
                                    .clickable { showAddExerciseDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                FitnessText(
                                    text = "➕",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { FitnessText("Search exercises, difficulty...") },
                        leadingIcon = {
                            FitnessText(
                                text = "🔍",
                                fontSize = 20.sp
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                FitnessText(
                                    text = "❌",
                                    fontSize = 16.sp,
                                    modifier = Modifier.clickable { searchQuery = "" }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quick Stats
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        item {
                            QuickStatCard(
                                title = "Total",
                                value = "${enhancedExercises.size}",
                                emoji = "🏋️",
                                color = currentTheme.primary
                            )
                        }
                        item {
                            QuickStatCard(
                                title = "Favorites",
                                value = "${enhancedExercises.count { it.isFavorite || favoriteExercises.contains(it.id) }}",
                                emoji = "❤️",
                                color = Color.Red
                            )
                        }
                        item {
                            QuickStatCard(
                                title = "Categories",
                                value = "${ExerciseCategory.values().size}",
                                emoji = "📂",
                                color = currentTheme.accent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Filter Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            item {
                                FilterChip(
                                    onClick = { selectedCategory = null },
                                    label = { FitnessText("All", fontSize = 12.sp) },
                                    selected = selectedCategory == null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                        selectedLabelColor = currentTheme.accent
                                    )
                                )
                            }

                            items(ExerciseCategory.values()) { category ->
                                FilterChip(
                                    onClick = { selectedCategory = category },
                                    label = { FitnessText("${category.emoji} ${category.displayName}", fontSize = 12.sp) },
                                    selected = selectedCategory == category,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                        selectedLabelColor = currentTheme.accent
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(currentTheme.accent.copy(alpha = 0.1f))
                                .clickable { showFilterDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            FitnessText(
                                text = "⚙️",
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            item {
                // Exercises Display
                if (filteredExercises.isEmpty()) {
                    EmptyWorkoutsState(
                        onAddClick = { showAddExerciseDialog = true },
                        currentTheme = currentTheme,
                        isFiltered = searchQuery.isNotEmpty() || selectedCategory != null
                    )
                } else {
                    if (viewMode == ViewMode.GRID) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .height(((filteredExercises.size + 1) / 2 * 300).dp)
                                .padding(horizontal = 16.dp),
                            userScrollEnabled = false
                        ) {
                            items(filteredExercises) { exercise ->
                                EnhancedExerciseCard(
                                    exercise = exercise,
                                    currentTheme = currentTheme,
                                    onFavoriteClick = {
                                        favoriteExercises = if (favoriteExercises.contains(exercise.id)) {
                                            favoriteExercises - exercise.id
                                        } else {
                                            favoriteExercises + exercise.id
                                        }
                                    },
                                    onClick = { selectedExercise = exercise },
                                    isFavorite = exercise.isFavorite || favoriteExercises.contains(exercise.id)
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            filteredExercises.forEach { exercise ->
                                ExerciseListCard(
                                    exercise = exercise,
                                    currentTheme = currentTheme,
                                    onFavoriteClick = {
                                        favoriteExercises = if (favoriteExercises.contains(exercise.id)) {
                                            favoriteExercises - exercise.id
                                        } else {
                                            favoriteExercises + exercise.id
                                        }
                                    },
                                    onClick = { selectedExercise = exercise },
                                    isFavorite = exercise.isFavorite || favoriteExercises.contains(exercise.id)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // Dialogs
        if (showAddExerciseDialog) {
            AddExerciseDialog(
                showDialog = showAddExerciseDialog,
                onDismiss = { showAddExerciseDialog = false },
                onAddExercise = { newExercise ->
                    enhancedExercises.add(newExercise)
                    showAddExerciseDialog = false
                },
                currentTheme = currentTheme
            )
        }

        if (selectedExercise != null) {
            ExerciseDetailsDialog(
                exercise = selectedExercise!!,
                onDismiss = { selectedExercise = null },
                currentTheme = currentTheme,
                onStartWorkout = {
                    selectedExercise = null
                }
            )
        }
    }
}

@Composable
fun EmptyWorkoutsState(
    onAddClick: () -> Unit,
    currentTheme: FitnessTheme,
    isFiltered: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FitnessText(
            text = if (isFiltered) "🔍" else "💪",
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitnessText(
            text = if (isFiltered) "No exercises found" else "Ready to start?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        FitnessText(
            text = if (isFiltered) "Try adjusting your search or filters" else "Add your first exercise and begin your fitness journey",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!isFiltered) {
            FitnessButton(
                onClick = onAddClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = currentTheme.accent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                FitnessText(
                    text = "➕",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                FitnessText("Add First Exercise", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}



// Part 7: Exercise Cards & Dialogs

@Composable
fun EnhancedExerciseCard(
    exercise: WorkoutExercise,
    currentTheme: FitnessTheme,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Image with overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = exercise.imageUrl,
                    contentDescription = exercise.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f)
                                )
                            )
                        )
                )

                // Category badge
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(exercise.category.name.let {
                            when(it) {
                                "CARDIO" -> Color(0xFFFF6B35)
                                "STRENGTH" -> Color(0xFF1E88E5)
                                "YOGA" -> Color(0xFF43A047)
                                "HIIT" -> Color(0xFF8E24AA)
                                "FLEXIBILITY" -> Color(0xFFFF9800)
                                "SPORTS" -> Color(0xFFE91E63)
                                else -> currentTheme.accent
                            }
                        })
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    FitnessText(
                        text = "${exercise.category.emoji} ${exercise.category.displayName}",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Favorite button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .clickable { onFavoriteClick() },
                    contentAlignment = Alignment.Center
                ) {
                    FitnessText(
                        text = if (isFavorite) "❤️" else "🤍",
                        fontSize = 16.sp
                    )
                }

                // Difficulty indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when(exercise.difficulty) {
                                "Easy" -> Color(0xFF4CAF50)
                                "Medium" -> Color(0xFFFF9800)
                                "Hard" -> Color(0xFFE53935)
                                else -> currentTheme.accent
                            }.copy(alpha = 0.9f)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    FitnessText(
                        text = exercise.difficulty,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Content
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                FitnessText(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FitnessText(
                            text = "⏱️",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        FitnessText(
                            text = exercise.duration,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FitnessText(
                            text = "🔥",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        FitnessText(
                            text = exercise.calories,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Start button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(currentTheme.accent.copy(alpha = 0.1f))
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    FitnessText(
                        text = "Start Workout ▶️",
                        color = currentTheme.accent,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseListCard(
    exercise: WorkoutExercise,
    currentTheme: FitnessTheme,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = exercise.imageUrl,
                    contentDescription = exercise.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    FitnessText(
                        text = if (isFavorite) "❤️" else "🤍",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { onFavoriteClick() }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = exercise.category.emoji,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FitnessText(
                        text = "${exercise.duration} • ${exercise.calories} • ${exercise.difficulty}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddExercise: (WorkoutExercise) -> Unit,
    currentTheme: FitnessTheme
) {
    if (showDialog) {
        var name by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf(ExerciseCategory.CARDIO) }
        var duration by remember { mutableStateOf("") }
        var difficulty by remember { mutableStateOf("Easy") }
        var calories by remember { mutableStateOf("") }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Add New Exercise 💪",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Exercise Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { FitnessText("Exercise Name") },
                        leadingIcon = {
                            FitnessText(
                                text = "🏋️",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Category Selection
                    FitnessText(
                        text = "Category",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ExerciseCategory.values()) { category ->
                            FilterChip(
                                onClick = { selectedCategory = category },
                                label = { FitnessText("${category.emoji} ${category.displayName}") },
                                selected = selectedCategory == category,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                    selectedLabelColor = currentTheme.accent
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Duration and Calories
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = duration,
                            onValueChange = { duration = it },
                            label = { FitnessText("Duration") },
                            placeholder = { FitnessText("30 min") },
                            leadingIcon = {
                                FitnessText(
                                    text = "⏱️",
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = currentTheme.accent,
                                focusedLabelColor = currentTheme.accent
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = calories,
                            onValueChange = { calories = it },
                            label = { FitnessText("Calories") },
                            placeholder = { FitnessText("300 cal") },
                            leadingIcon = {
                                FitnessText(
                                    text = "🔥",
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = currentTheme.accent,
                                focusedLabelColor = currentTheme.accent
                            ),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Difficulty Selection
                    FitnessText(
                        text = "Difficulty",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Easy", "Medium", "Hard").forEach { diff ->
                            FilterChip(
                                onClick = { difficulty = diff },
                                label = {
                                    FitnessText(
                                        "${when(diff) {
                                            "Easy" -> "🟢"
                                            "Medium" -> "🟡"
                                            "Hard" -> "🔴"
                                            else -> "⚪"
                                        }} $diff"
                                    )
                                },
                                selected = difficulty == diff,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                    selectedLabelColor = currentTheme.accent
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            FitnessText("Cancel")
                        }

                        FitnessButton(
                            onClick = {
                                if (name.isNotBlank()) {
                                    val imageUrl = when(selectedCategory) {
                                        ExerciseCategory.CARDIO -> FitnessImages.cardioExercises.random()
                                        ExerciseCategory.STRENGTH -> FitnessImages.strengthExercises.random()
                                        ExerciseCategory.YOGA -> FitnessImages.yogaExercises.random()
                                        ExerciseCategory.HIIT -> FitnessImages.hiitExercises.random()
                                        else -> FitnessImages.cardioExercises.random()
                                    }

                                    val newExercise = WorkoutExercise(
                                        id = System.currentTimeMillis().toString(),
                                        name = name,
                                        category = selectedCategory,
                                        imageUrl = imageUrl,
                                        duration = duration.ifBlank { "30 min" },
                                        difficulty = difficulty,
                                        calories = calories.ifBlank { "200 cal" }
                                    )
                                    onAddExercise(newExercise)
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentTheme.accent
                            ),
                            enabled = name.isNotBlank()
                        ) {
                            FitnessText("Add Exercise", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseDetailsDialog(
    exercise: WorkoutExercise,
    onDismiss: () -> Unit,
    currentTheme: FitnessTheme,
    onStartWorkout: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                // Header Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = exercise.imageUrl,
                        contentDescription = exercise.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            )
                    )

                    // Close button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        FitnessText(
                            text = "❌",
                            fontSize = 16.sp
                        )
                    }

                    // Title overlay
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        FitnessText(
                            text = exercise.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        FitnessText(
                            text = "${exercise.category.emoji} ${exercise.category.displayName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                // Content
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = "⏱️",
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = exercise.duration,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Duration",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = "🔥",
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = exercise.calories,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Calories",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = when(exercise.difficulty) {
                                    "Easy" -> "🟢"
                                    "Medium" -> "🟡"
                                    "Hard" -> "🔴"
                                    else -> "⚪"
                                },
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = exercise.difficulty,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Difficulty",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    FitnessText(
                        text = "About this Exercise",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FitnessText(
                        text = "This ${exercise.category.displayName.lowercase()} workout will help you build strength, improve endurance, and burn calories effectively. Perfect for ${exercise.difficulty.lowercase()} level fitness enthusiasts.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            FitnessText("Close")
                        }


                    }
                }
            }
        }
    }
}

// Part 8: Routines Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme
) {
    var searchQuery by remember { mutableStateOf("") }
    var showCreateRoutineDialog by remember { mutableStateOf(false) }
    var selectedRoutine by remember { mutableStateOf<WorkoutRoutine?>(null) }
    var favoriteRoutines by remember { mutableStateOf(setOf<String>()) }
    var selectedFilter by remember { mutableStateOf("All") }

    // Enhanced sample routines
    val enhancedRoutines = remember {
        mutableStateListOf<WorkoutRoutine>().apply {
            addAll(listOf(
                WorkoutRoutine(
                    "1", "Morning Energy Boost",
                    SampleFitnessData.sampleExercises.take(3),
                    "Full Body",
                    "Today",
                    true,
                    FitnessImages.workoutRoutines[0],
                    "90 min",
                    "650 cal"
                ),
                WorkoutRoutine(
                    "2", "Strength Builder Pro",
                    listOf(SampleFitnessData.sampleExercises[1], SampleFitnessData.sampleExercises[4]),
                    "Strength",
                    "Yesterday",
                    false,
                    FitnessImages.workoutRoutines[1],
                    "75 min",
                    "500 cal"
                ),
                WorkoutRoutine(
                    "3", "Recovery & Flexibility",
                    listOf(SampleFitnessData.sampleExercises[2], SampleFitnessData.sampleExercises[5]),
                    "Recovery",
                    "Tomorrow",
                    true,
                    FitnessImages.workoutRoutines[2],
                    "65 min",
                    "300 cal"
                ),
                WorkoutRoutine(
                    "4", "HIIT Power Session",
                    listOf(SampleFitnessData.sampleExercises[3], SampleFitnessData.sampleExercises[0]),
                    "HIIT",
                    "Today",
                    false,
                    FitnessImages.workoutRoutines[3],
                    "45 min",
                    "550 cal"
                ),
                WorkoutRoutine(
                    "5", "Cardio Blast",
                    listOf(SampleFitnessData.sampleExercises[0], SampleFitnessData.sampleExercises[3]),
                    "Cardio",
                    "3 days ago",
                    true,
                    FitnessImages.workoutRoutines[0],
                    "60 min",
                    "480 cal"
                )
            ))
        }
    }

    // Filter routines
    val filteredRoutines = enhancedRoutines.filter { routine ->
        val matchesSearch = routine.name.contains(searchQuery, ignoreCase = true) ||
                routine.type.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Favorites" -> routine.isFavorite || favoriteRoutines.contains(routine.id)
            "Recent" -> routine.date.contains("Today") || routine.date.contains("Yesterday")
            "All" -> true
            else -> routine.type == selectedFilter
        }
        matchesSearch && matchesFilter
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Column {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            FitnessText(
                                text = "My Routines 📋",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            FitnessText(
                                text = "${filteredRoutines.size} routines ready",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(currentTheme.accent)
                                .clickable { showCreateRoutineDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            FitnessText(
                                text = "➕",
                                fontSize = 20.sp
                            )
                        }
                    }

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { FitnessText("Search routines, types...") },
                        leadingIcon = {
                            FitnessText(
                                text = "🔍",
                                fontSize = 20.sp
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                FitnessText(
                                    text = "❌",
                                    fontSize = 16.sp,
                                    modifier = Modifier.clickable { searchQuery = "" }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quick Stats
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        item {
                            QuickStatCard(
                                title = "Total",
                                value = "${enhancedRoutines.size}",
                                emoji = "📋",
                                color = currentTheme.primary
                            )
                        }
                        item {
                            QuickStatCard(
                                title = "Favorites",
                                value = "${enhancedRoutines.count { it.isFavorite || favoriteRoutines.contains(it.id) }}",
                                emoji = "❤️",
                                color = Color.Red
                            )
                        }
                        item {
                            QuickStatCard(
                                title = "This Week",
                                value = "5",
                                emoji = "📅",
                                color = currentTheme.accent
                            )
                        }
                        item {
                            QuickStatCard(
                                title = "Avg Duration",
                                value = "68 min",
                                emoji = "⏱️",
                                color = Color(0xFF9C27B0)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Filter Row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        val filters = listOf("All", "Favorites", "Recent", "Full Body", "Strength", "Cardio", "HIIT", "Recovery")
                        items(filters) { filter ->
                            FilterChip(
                                onClick = { selectedFilter = filter },
                                label = {
                                    FitnessText(
                                        "${when(filter) {
                                            "All" -> "📋"
                                            "Favorites" -> "❤️"
                                            "Recent" -> "🕐"
                                            "Full Body" -> "🏋️"
                                            "Strength" -> "💪"
                                            "Cardio" -> "🏃"
                                            "HIIT" -> "⚡"
                                            "Recovery" -> "🧘"
                                            else -> "📁"
                                        }} $filter",
                                        fontSize = 12.sp
                                    )
                                },
                                selected = selectedFilter == filter,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                    selectedLabelColor = currentTheme.accent
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            item {
                // Routines Display
                if (filteredRoutines.isEmpty()) {
                    EmptyRoutinesState(
                        onCreateClick = { showCreateRoutineDialog = true },
                        currentTheme = currentTheme,
                        isFiltered = searchQuery.isNotEmpty() || selectedFilter != "All"
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .height((filteredRoutines.size * 200).dp)
                            .padding(horizontal = 16.dp),
                        userScrollEnabled = false
                    ) {
                        items(filteredRoutines) { routine ->
                            RoutineCard(
                                routine = routine,
                                currentTheme = currentTheme,
                                onFavoriteClick = {
                                    favoriteRoutines = if (favoriteRoutines.contains(routine.id)) {
                                        favoriteRoutines - routine.id
                                    } else {
                                        favoriteRoutines + routine.id
                                    }
                                },
                                onClick = { selectedRoutine = routine },
                                isFavorite = routine.isFavorite || favoriteRoutines.contains(routine.id)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // Dialogs
        if (showCreateRoutineDialog) {
            CreateRoutineDialog(
                showDialog = showCreateRoutineDialog,
                onDismiss = { showCreateRoutineDialog = false },
                onCreateRoutine = { newRoutine ->
                    enhancedRoutines.add(newRoutine)
                    showCreateRoutineDialog = false
                },
                currentTheme = currentTheme
            )
        }

        if (selectedRoutine != null) {
            RoutineDetailsDialog(
                routine = selectedRoutine!!,
                onDismiss = { selectedRoutine = null },
                currentTheme = currentTheme,
                onStartRoutine = {
                    selectedRoutine = null
                }
            )
        }
    }
}

@Composable
fun EmptyRoutinesState(
    onCreateClick: () -> Unit,
    currentTheme: FitnessTheme,
    isFiltered: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FitnessText(
            text = if (isFiltered) "🔍" else "📋",
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitnessText(
            text = if (isFiltered) "No routines found" else "Create your first routine",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        FitnessText(
            text = if (isFiltered) "Try adjusting your search or filters" else "Combine exercises into powerful workout routines",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!isFiltered) {
            FitnessButton(
                onClick = onCreateClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = currentTheme.accent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                FitnessText(
                    text = "➕",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                FitnessText("Create First Routine", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun RoutineCard(
    routine: WorkoutRoutine,
    currentTheme: FitnessTheme,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row {
            // Image Section
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = routine.imageUrl,
                    contentDescription = routine.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f)
                                )
                            )
                        )
                )

                // Exercise count badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(currentTheme.accent.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    FitnessText(
                        text = "${routine.exercises.size} exercises",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = routine.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    FitnessText(
                        text = if (isFavorite) "❤️" else "🤍",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { onFavoriteClick() }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Type and Date
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = when(routine.type) {
                            "Full Body" -> "🏋️"
                            "Strength" -> "💪"
                            "Cardio" -> "🏃"
                            "HIIT" -> "⚡"
                            "Recovery" -> "🧘"
                            else -> "📋"
                        },
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FitnessText(
                        text = "${routine.type} • ${routine.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FitnessText(
                            text = "⏱️",
                            fontSize = 16.sp
                        )
                        FitnessText(
                            text = routine.duration,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = currentTheme.accent
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FitnessText(
                            text = "🔥",
                            fontSize = 16.sp
                        )
                        FitnessText(
                            text = routine.calories,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = currentTheme.accent
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FitnessText(
                            text = "💪",
                            fontSize = 16.sp
                        )
                        FitnessText(
                            text = "${routine.exercises.size}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = currentTheme.accent
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Start Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(currentTheme.accent.copy(alpha = 0.1f))
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    FitnessText(
                        text = "Start Routine ▶️",
                        color = currentTheme.accent,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoutineDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCreateRoutine: (WorkoutRoutine) -> Unit,
    currentTheme: FitnessTheme
) {
    if (showDialog) {
        var routineName by remember { mutableStateOf("") }
        var routineType by remember { mutableStateOf("Full Body") }
        var selectedExercises by remember { mutableStateOf(listOf<WorkoutExercise>()) }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Create Routine 📋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Routine Name
                    OutlinedTextField(
                        value = routineName,
                        onValueChange = { routineName = it },
                        label = { FitnessText("Routine Name") },
                        leadingIcon = {
                            FitnessText(
                                text = "📋",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Routine Type
                    FitnessText(
                        text = "Routine Type",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val types = listOf("Full Body", "Strength", "Cardio", "HIIT", "Recovery")
                        items(types) { type ->
                            FilterChip(
                                onClick = { routineType = type },
                                label = {
                                    FitnessText(
                                        "${when(type) {
                                            "Full Body" -> "🏋️"
                                            "Strength" -> "💪"
                                            "Cardio" -> "🏃"
                                            "HIIT" -> "⚡"
                                            "Recovery" -> "🧘"
                                            else -> "📋"
                                        }} $type"
                                    )
                                },
                                selected = routineType == type,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                    selectedLabelColor = currentTheme.accent
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Exercise Selection
                    FitnessText(
                        text = "Select Exercises (${selectedExercises.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(SampleFitnessData.sampleExercises) { exercise ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedExercises = if (selectedExercises.contains(exercise)) {
                                            selectedExercises - exercise
                                        } else {
                                            selectedExercises + exercise
                                        }
                                    }
                                    .background(
                                        if (selectedExercises.contains(exercise))
                                            currentTheme.accent.copy(alpha = 0.1f)
                                        else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FitnessText(
                                    text = if (selectedExercises.contains(exercise)) "✅" else "⚪",
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    FitnessText(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    FitnessText(
                                        text = "${exercise.category.emoji} ${exercise.duration} • ${exercise.calories}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            FitnessText("Cancel")
                        }

                        FitnessButton(
                            onClick = {
                                if (routineName.isNotBlank() && selectedExercises.isNotEmpty()) {
                                    val totalDuration = selectedExercises.sumOf {
                                        it.duration.replace(" min", "").toIntOrNull() ?: 30
                                    }
                                    val totalCalories = selectedExercises.sumOf {
                                        it.calories.replace(" cal", "").toIntOrNull() ?: 200
                                    }

                                    val newRoutine = WorkoutRoutine(
                                        id = System.currentTimeMillis().toString(),
                                        name = routineName,
                                        exercises = selectedExercises,
                                        type = routineType,
                                        date = "Today",
                                        isFavorite = false,
                                        imageUrl = FitnessImages.workoutRoutines.random(),
                                        duration = "$totalDuration min",
                                        calories = "$totalCalories cal"
                                    )
                                    onCreateRoutine(newRoutine)
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentTheme.accent
                            ),
                            enabled = routineName.isNotBlank() && selectedExercises.isNotEmpty()
                        ) {
                            FitnessText("Create Routine", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineDetailsDialog(
    routine: WorkoutRoutine,
    onDismiss: () -> Unit,
    currentTheme: FitnessTheme,
    onStartRoutine: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                // Header Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = routine.imageUrl,
                        contentDescription = routine.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    // Close button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        FitnessText(
                            text = "❌",
                            fontSize = 16.sp
                        )
                    }

                    // Title overlay
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        FitnessText(
                            text = routine.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        FitnessText(
                            text = "${when(routine.type) {
                                "Full Body" -> "🏋️"
                                "Strength" -> "💪"
                                "Cardio" -> "🏃"
                                "HIIT" -> "⚡"
                                "Recovery" -> "🧘"
                                else -> "📋"
                            }} ${routine.type} • ${routine.exercises.size} exercises",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                // Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(24.dp)
                ) {
                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = "⏱️",
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = routine.duration,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Duration",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = "🔥",
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = routine.calories,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Calories",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FitnessText(
                                text = "💪",
                                fontSize = 24.sp
                            )
                            FitnessText(
                                text = "${routine.exercises.size}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = currentTheme.accent
                            )
                            FitnessText(
                                text = "Exercises",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Exercises List
                    FitnessText(
                        text = "Exercises in this Routine",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(routine.exercises) { index, exercise ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FitnessText(
                                    text = "${index + 1}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = currentTheme.accent,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(currentTheme.accent.copy(alpha = 0.1f))
                                        .wrapContentSize()
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    FitnessText(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    FitnessText(
                                        text = "${exercise.category.emoji} ${exercise.duration} • ${exercise.calories}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        FitnessText("Close")
                    }

                    FitnessButton(
                        onClick = onStartRoutine,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = currentTheme.accent
                        )
                    ) {
                        FitnessText("Start Routine ▶️", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// Part 9: Progress Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme
) {
    var selectedTimeFrame by remember { mutableStateOf("Week") }
    var showStatsDialog by remember { mutableStateOf(false) }

    // Sample progress data
    val weeklyStats = remember {
        listOf(
            ProgressData("Mon", 45, 320),
            ProgressData("Tue", 60, 450),
            ProgressData("Wed", 30, 250),
            ProgressData("Thu", 75, 520),
            ProgressData("Fri", 50, 380),
            ProgressData("Sat", 90, 650),
            ProgressData("Sun", 40, 300)
        )
    }

    val monthlyGoals = remember {
        listOf(
            GoalData("Workout Days", 20, 15, "🗓️"),
            GoalData("Total Hours", 40, 32, "⏱️"),
            GoalData("Calories Burned", 8000, 6420, "🔥"),
            GoalData("Weight Goal", 5, 3, "⚖️")
        )
    }

    val achievements = remember {
        listOf(
            Achievement("First Workout", "Complete your first exercise", true, "🏆"),
            Achievement("Week Warrior", "Work out 5 days in a week", true, "💪"),
            Achievement("Calorie Crusher", "Burn 1000 calories in a day", false, "🔥"),
            Achievement("Consistency King", "Work out 30 days straight", false, "👑"),
            Achievement("Strength Builder", "Complete 50 strength exercises", true, "🏋️"),
            Achievement("Flexibility Master", "Complete 25 yoga sessions", false, "🧘")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            // Header
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        FitnessText(
                            text = "My Progress 📊",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        FitnessText(
                            text = "Track your fitness journey",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(currentTheme.accent.copy(alpha = 0.1f))
                            .clickable { showStatsDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        FitnessText(
                            text = "📈",
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Time Frame Selection
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val timeFrames = listOf("Week", "Month", "Year")
                    items(timeFrames) { timeFrame ->
                        FilterChip(
                            onClick = { selectedTimeFrame = timeFrame },
                            label = {
                                FitnessText(
                                    "${when(timeFrame) {
                                        "Week" -> "📅"
                                        "Month" -> "🗓️"
                                        "Year" -> "📆"
                                        else -> "📊"
                                    }} $timeFrame",
                                    fontSize = 12.sp
                                )
                            },
                            selected = selectedTimeFrame == timeFrame,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = currentTheme.accent.copy(alpha = 0.2f),
                                selectedLabelColor = currentTheme.accent
                            )
                        )
                    }
                }
            }
        }

        item {
            // Weekly Overview Stats
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    ProgressStatCard(
                        title = "This Week",
                        value = "5 workouts",
                        subtitle = "+2 from last week",
                        emoji = "💪",
                        color = currentTheme.primary,
                        trend = "up"
                    )
                }
                item {
                    ProgressStatCard(
                        title = "Total Time",
                        value = "6.5 hours",
                        subtitle = "+1.2h from last week",
                        emoji = "⏱️",
                        color = Color(0xFF9C27B0),
                        trend = "up"
                    )
                }
                item {
                    ProgressStatCard(
                        title = "Calories",
                        value = "2,140 cal",
                        subtitle = "320 cal/day avg",
                        emoji = "🔥",
                        color = Color(0xFFE65100),
                        trend = "up"
                    )
                }
                item {
                    ProgressStatCard(
                        title = "Streak",
                        value = "12 days",
                        subtitle = "Personal best!",
                        emoji = "🔥",
                        color = Color(0xFFD32F2F),
                        trend = "up"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Activity Chart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Weekly Activity 📈",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FitnessText(
                                text = "⏱️",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            FitnessText(
                                text = "Minutes",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Simple Bar Chart
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        weeklyStats.forEach { stat ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                FitnessText(
                                    text = "${stat.minutes}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = currentTheme.accent,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height((stat.minutes * 1.5f).dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                                colors = listOf(
                                                    currentTheme.accent,
                                                    currentTheme.accent.copy(alpha = 0.6f)
                                                )
                                            )
                                        )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                FitnessText(
                                    text = stat.day,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chart Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FitnessText(
                            text = "📊 Average: 56 min/day",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Monthly Goals Section
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = "Monthly Goals 🎯",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    FitnessText(
                        text = "3/4 completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = currentTheme.accent,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                monthlyGoals.forEach { goal ->
                    GoalProgressCard(
                        goal = goal,
                        currentTheme = currentTheme
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Achievements Section
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FitnessText(
                        text = "Achievements 🏆",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    FitnessText(
                        text = "${achievements.count { it.isUnlocked }}/${achievements.size}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = currentTheme.accent,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .height((achievements.size / 2 * 120).dp),
                    userScrollEnabled = false
                ) {
                    items(achievements) { achievement ->
                        AchievementCard(
                            achievement = achievement,
                            currentTheme = currentTheme
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Stats Dialog
    if (showStatsDialog) {
        DetailedStatsDialog(
            showDialog = showStatsDialog,
            onDismiss = { showStatsDialog = false },
            currentTheme = currentTheme,
            weeklyStats = weeklyStats
        )
    }
}

@Composable
fun ProgressStatCard(
    title: String,
    value: String,
    subtitle: String,
    emoji: String,
    color: Color,
    trend: String = "neutral"
) {
    Card(
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FitnessText(
                    text = emoji,
                    fontSize = 24.sp
                )

                FitnessText(
                    text = when(trend) {
                        "up" -> "📈"
                        "down" -> "📉"
                        else -> "➡️"
                    },
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            FitnessText(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )

            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            FitnessText(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = when(trend) {
                    "up" -> Color(0xFF4CAF50)
                    "down" -> Color(0xFFE53935)
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                },
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun GoalProgressCard(
    goal: GoalData,
    currentTheme: FitnessTheme
) {
    val progress = (goal.current.toFloat() / goal.target.toFloat()).coerceIn(0f, 1f)
    val isCompleted = goal.current >= goal.target

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted)
                currentTheme.accent.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FitnessText(
                        text = goal.emoji,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        FitnessText(
                            text = goal.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        FitnessText(
                            text = "${goal.current}/${goal.target}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                if (isCompleted) {
                    FitnessText(
                        text = "✅",
                        fontSize = 24.sp
                    )
                } else {
                    FitnessText(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = currentTheme.accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (isCompleted)
                                Color(0xFF4CAF50)
                            else currentTheme.accent
                        )
                )
            }
        }
    }
}

@Composable
fun AchievementCard(
    achievement: Achievement,
    currentTheme: FitnessTheme
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked)
                currentTheme.accent.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(if (achievement.isUnlocked) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FitnessText(
                text = achievement.emoji,
                fontSize = if (achievement.isUnlocked) 32.sp else 24.sp,
                modifier = Modifier.graphicsLayer(
                    alpha = if (achievement.isUnlocked) 1f else 0.5f
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            FitnessText(
                text = achievement.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = if (achievement.isUnlocked)
                    MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DetailedStatsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentTheme: FitnessTheme,
    weeklyStats: List<ProgressData>
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Detailed Stats 📊",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Detailed Stats
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            DetailedStatRow("Total Workouts", "47", "🏋️", currentTheme.primary)
                        }
                        item {
                            DetailedStatRow("Total Hours", "52.5", "⏱️", Color(0xFF9C27B0))
                        }
                        item {
                            DetailedStatRow("Calories Burned", "15,240", "🔥", Color(0xFFE65100))
                        }
                        item {
                            DetailedStatRow("Average Session", "67 min", "📊", currentTheme.accent)
                        }
                        item {
                            DetailedStatRow("Longest Streak", "18 days", "📅", Color(0xFF4CAF50))
                        }
                        item {
                            DetailedStatRow("Favorite Exercise", "Morning Run", "❤️", Color.Red)
                        }
                        item {
                            DetailedStatRow("Most Active Day", "Saturday", "📈", currentTheme.primary)
                        }
                        item {
                            DetailedStatRow("Personal Best", "120 min", "🏆", Color(0xFFFFD700))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FitnessButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = currentTheme.accent
                        )
                    ) {
                        FitnessText("Close", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailedStatRow(
    label: String,
    value: String,
    emoji: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color.copy(alpha = 0.1f),
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            FitnessText(
                text = emoji,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            FitnessText(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        FitnessText(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

// Data classes for Progress Screen
data class ProgressData(
    val day: String,
    val minutes: Int,
    val calories: Int
)

data class GoalData(
    val name: String,
    val target: Int,
    val current: Int,
    val emoji: String
)

data class Achievement(
    val name: String,
    val description: String,
    val isUnlocked: Boolean,
    val emoji: String
)


// Part 10: Profile Screen & Dialogs - Complete Implementation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessProfileScreen(
    viewModel: UserPreferencesViewModel,
    userPreferences: UserPreferences,
    currentTheme: FitnessTheme,
    onLogout: () -> Unit = {}
) {
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            // Header with Profile Info
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                FitnessText(
                    text = "My Profile 👤",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Profile Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Picture
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                        colors = listOf(currentTheme.primary, currentTheme.accent)
                                    )
                                )
                                .clickable { showEditProfileDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (userPreferences.profilePicturePath.isNotEmpty()) {
                                AsyncImage(
                                    model = userPreferences.profilePicturePath,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                FitnessText(
                                    text = userPreferences.userName.take(1).uppercase().ifEmpty { "U" },
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        FitnessText(
                            text = userPreferences.userName.ifEmpty { "Fitness Enthusiast" },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        FitnessText(
                            text = userPreferences.userEmail.ifEmpty { "user@fitness.com" },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FitnessStat("47", "Workouts", "🏋️")
                            FitnessStat("18", "Streak", "🔥")
                            FitnessStat("52h", "Total Time", "⏱️")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        FitnessButton(
                            onClick = { showEditProfileDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentTheme.accent.copy(alpha = 0.1f),
                                contentColor = currentTheme.accent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            FitnessText("Edit Profile ✏️", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Settings Section
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                FitnessText(
                    text = "Settings ⚙️",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SettingsItem(
                            emoji = "🎨",
                            title = "Theme",
                            subtitle = currentTheme.name,
                            onClick = { showThemeDialog = true },
                            currentTheme = currentTheme
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        SettingsItem(
                            emoji = "🌍",
                            title = "Language",
                            subtitle = availableLanguages.find { it.code == userPreferences.selectedLanguage }?.name ?: "English",
                            onClick = { showLanguageDialog = true },
                            currentTheme = currentTheme
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FitnessText(
                                    text = "🔔",
                                    fontSize = 24.sp
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    FitnessText(
                                        text = "Notifications",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    FitnessText(
                                        text = if (userPreferences.notificationsEnabled) "Enabled" else "Disabled",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }

                            Switch(
                                checked = userPreferences.notificationsEnabled,
                                onCheckedChange = {
                                    coroutineScope.launch {
                                        viewModel.saveNotificationsEnabled(it)
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = currentTheme.accent,
                                    checkedTrackColor = currentTheme.accent.copy(alpha = 0.5f)
                                )
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FitnessText(
                                    text = if (userPreferences.darkModeEnabled) "🌙" else "☀️",
                                    fontSize = 24.sp
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    FitnessText(
                                        text = "Dark Mode",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    FitnessText(
                                        text = if (userPreferences.darkModeEnabled) "Dark theme" else "Light theme",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }

                            Switch(
                                checked = userPreferences.darkModeEnabled,
                                onCheckedChange = {
                                    coroutineScope.launch {
                                        viewModel.saveDarkModeEnabled(it)
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = currentTheme.accent,
                                    checkedTrackColor = currentTheme.accent.copy(alpha = 0.5f)
                                )
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )


                    }
                }
            }
        }



        item {
            Spacer(modifier = Modifier.height(32.dp))

            // Logout Button
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                FitnessButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.logout()
                            onLogout()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935).copy(alpha = 0.1f),
                        contentColor = Color(0xFFE53935)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    FitnessText(
                        text = "🚪",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FitnessText("Sign Out", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // App Version
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FitnessText(
                        text = "FitTracker Pro v1.0.0",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Dialogs
    if (showEditProfileDialog) {
        EditProfileDialog(
            showDialog = showEditProfileDialog,
            onDismiss = { showEditProfileDialog = false },
            userPreferences = userPreferences,
            viewModel = viewModel,
            currentTheme = currentTheme
        )
    }

    if (showThemeDialog) {
        ThemeSelectionDialog(
            showDialog = showThemeDialog,
            onDismiss = { showThemeDialog = false },
            currentTheme = userPreferences.selectedTheme,
            onThemeSelected = { theme ->
                coroutineScope.launch {
                    viewModel.saveSelectedTheme(theme)
                }
                showThemeDialog = false
            },
            currentThemeStyle = currentTheme
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            showDialog = showLanguageDialog,
            onDismiss = { showLanguageDialog = false },
            currentLanguage = userPreferences.selectedLanguage,
            onLanguageSelected = { language ->
                coroutineScope.launch {
                    viewModel.saveSelectedLanguage(language)
                }
                showLanguageDialog = false
            },
            currentTheme = currentTheme
        )
    }

    if (showSettingsDialog) {
        AdvancedSettingsDialog(
            showDialog = showSettingsDialog,
            onDismiss = { showSettingsDialog = false },
            currentTheme = currentTheme
        )
    }
}

@Composable
fun QuickActionCard(
    title: String,
    emoji: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FitnessText(
                text = emoji,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    userPreferences: UserPreferences,
    viewModel: UserPreferencesViewModel,
    currentTheme: FitnessTheme
) {
    if (showDialog) {
        var name by remember { mutableStateOf(userPreferences.userName) }
        var email by remember { mutableStateOf(userPreferences.userEmail) }
        val coroutineScope = rememberCoroutineScope()

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Edit Profile ✏️",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Profile Picture Section
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                        colors = listOf(currentTheme.primary, currentTheme.accent)
                                    )
                                )
                                .clickable { /* Photo picker functionality */ },
                            contentAlignment = Alignment.Center
                        ) {
                            FitnessText(
                                text = name.take(1).uppercase().ifEmpty { "U" },
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))


                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { FitnessText("Full Name") },
                        leadingIcon = {
                            FitnessText(
                                text = "👤",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { FitnessText("Email") },
                        leadingIcon = {
                            FitnessText(
                                text = "📧",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = currentTheme.accent,
                            focusedLabelColor = currentTheme.accent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            FitnessText("Cancel")
                        }

                        FitnessButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.saveUserName(name)
                                    viewModel.saveUserEmail(email)
                                }
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentTheme.accent
                            )
                        ) {
                            FitnessText("Save Changes", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSelectionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentTheme: String,
    onThemeSelected: (String) -> Unit,
    currentThemeStyle: FitnessTheme
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Choose Theme 🎨",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentThemeStyle.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val themes = listOf(
                        FitnessTheme.Energetic,
                        FitnessTheme.Strong,
                        FitnessTheme.Vibrant,
                        FitnessTheme.Power
                    )

                    themes.forEach { theme ->
                        ThemeSelectionItem(
                            theme = theme,
                            isSelected = currentTheme == theme.name,
                            onSelect = { onThemeSelected(theme.name) }
                        )
                        if (theme != themes.last()) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSelectionItem(
    theme: FitnessTheme,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .background(
                if (isSelected) theme.primary.copy(alpha = 0.1f) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color preview
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(theme.primary)
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(theme.secondary)
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(theme.accent)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            FitnessText(
                text = theme.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (isSelected) {
            FitnessText(
                text = "✅",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    currentTheme: FitnessTheme
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Select Language 🌍",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    availableLanguages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLanguageSelected(language.code) }
                                .background(
                                    if (currentLanguage == language.code)
                                        currentTheme.accent.copy(alpha = 0.1f)
                                    else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FitnessText(
                                text = language.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )

                            if (currentLanguage == language.code) {
                                FitnessText(
                                    text = "✅",
                                    fontSize = 20.sp
                                )
                            }
                        }
                        if (language != availableLanguages.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdvancedSettingsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentTheme: FitnessTheme
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FitnessText(
                            text = "Advanced Settings ⚙️",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.primary
                        )

                        FitnessText(
                            text = "❌",
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            AdvancedSettingItem(
                                emoji = "🔄",
                                title = "Sync Devices",
                                subtitle = "Sync across all your devices",
                                onClick = { /* Sync functionality */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "📱",
                                title = "Health App Integration",
                                subtitle = "Connect with Apple Health/Google Fit",
                                onClick = { /* Health app integration */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "🏃",
                                title = "Workout Reminders",
                                subtitle = "Set custom workout reminders",
                                onClick = { /* Reminder settings */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "📊",
                                title = "Analytics",
                                subtitle = "Detailed workout analytics",
                                onClick = { /* Analytics settings */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "🔒",
                                title = "Privacy",
                                subtitle = "Manage your privacy settings",
                                onClick = { /* Privacy settings */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "🗑️",
                                title = "Clear Data",
                                subtitle = "Reset all workout data",
                                onClick = { /* Clear data functionality */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "📋",
                                title = "Terms & Conditions",
                                subtitle = "Read our terms and conditions",
                                onClick = { /* Terms functionality */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "🔐",
                                title = "Privacy Policy",
                                subtitle = "Read our privacy policy",
                                onClick = { /* Privacy policy functionality */ }
                            )
                        }
                        item {
                            AdvancedSettingItem(
                                emoji = "ℹ️",
                                title = "About",
                                subtitle = "App information and credits",
                                onClick = { /* About functionality */ }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FitnessButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = currentTheme.accent
                        )
                    ) {
                        FitnessText("Close", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun AdvancedSettingItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FitnessText(
            text = emoji,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            FitnessText(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        FitnessText(
            text = "➡️",
            fontSize = 18.sp
        )
    }
}

// Additional Profile Components

@Composable
fun ProfileInfoCard(
    title: String,
    value: String,
    emoji: String,
    color: Color,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FitnessText(
                text = emoji,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                FitnessText(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                FitnessText(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            if (onClick != null) {
                FitnessText(
                    text = "➡️",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ProfileStatsSection(
    currentTheme: FitnessTheme
) {
    Column {
        FitnessText(
            text = "This Month 📅",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileInfoCard(
                title = "Workouts",
                value = "15",
                emoji = "🏋️",
                color = currentTheme.primary,
                modifier = Modifier.weight(1f)
            )

            ProfileInfoCard(
                title = "Hours",
                value = "18.5",
                emoji = "⏱️",
                color = Color(0xFF9C27B0),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileInfoCard(
                title = "Calories",
                value = "5,240",
                emoji = "🔥",
                color = Color(0xFFE65100),
                modifier = Modifier.weight(1f)
            )

            ProfileInfoCard(
                title = "Streak",
                value = "12 days",
                emoji = "📈",
                color = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProfileInfoCard(
    title: String,
    value: String,
    emoji: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FitnessText(
                text = emoji,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            FitnessText(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )

            FitnessText(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AchievementsBadgeSection(
    currentTheme: FitnessTheme
) {
    val recentAchievements = remember {
        listOf(
            "🏆 First Workout",
            "💪 Week Warrior",
            "🔥 Calorie Crusher",
            "📈 Consistency King"
        )
    }

    Column {
        FitnessText(
            text = "Recent Badges 🏆",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recentAchievements) { achievement ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = currentTheme.accent.copy(alpha = 0.1f)
                    )
                ) {
                    FitnessText(
                        text = achievement,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = currentTheme.accent,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Utility function for creating horizontal dividers
@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color)
    )
}

// Helper function to format user stats
fun formatUserStats(
    workouts: Int,
    totalHours: Double,
    calories: Int,
    streak: Int
): Map<String, String> {
    return mapOf(
        "workouts" to workouts.toString(),
        "hours" to "${totalHours}h",
        "calories" to "${calories}cal",
        "streak" to "${streak} days"
    )
}

// Extension function for theme colors
fun FitnessTheme.getColorByName(colorName: String): Color {
    return when (colorName.lowercase()) {
        "primary" -> this.primary
        "secondary" -> this.secondary
        "accent" -> this.accent
        "background" -> this.background
        "surface" -> this.surface
        "onsurface" -> this.onSurface
        else -> this.primary
    }
}

// Data class for user statistics
data class UserStats(
    val totalWorkouts: Int = 0,
    val totalHours: Double = 0.0,
    val totalCalories: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val favoriteExercise: String = "",
    val averageSessionDuration: Int = 0,
    val weeklyGoal: Int = 5,
    val monthlyGoal: Int = 20
)

// Extension function to get user level based on workouts
fun UserStats.getUserLevel(): Pair<String, String> {
    return when {
        totalWorkouts < 10 -> "Beginner" to "🌱"
        totalWorkouts < 50 -> "Intermediate" to "💪"
        totalWorkouts < 100 -> "Advanced" to "🏆"
        totalWorkouts < 200 -> "Expert" to "⭐"
        else -> "Master" to "👑"
    }
}

// Function to calculate workout completion percentage
fun calculateCompletionPercentage(current: Int, goal: Int): Float {
    return if (goal > 0) (current.toFloat() / goal.toFloat()).coerceIn(0f, 1f) else 0f
}
