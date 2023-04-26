package me.eriknikli.neptuncopy;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class ErrorHandling {
    public static String getErrorMessage(FirebaseAuthInvalidCredentialsException exception) {
        switch (exception.getErrorCode().toLowerCase()) {
            case "error_email_already_in_use":
                return "Ez az email cím alatt már létezik felhasználó.";

            case "error_internal_error":
                return "A Firebase nem működik, kérlek próbáld újra később.";

            case "error_invalid_email":
                return "Érvénytelen e-mail cím.";

            case "error_invalid_password":
                return "A jelszó nem megfelelő. Legalább 6 karakterhosszúnak kell lenni_e.";

            case "error_user_not_found":
                return "Nem létezik ilyen felhasználó.";

            case "error_wrong_password":
                return "Érvénytelen jelszó.";

        }
        return exception.getMessage();
    }
}
