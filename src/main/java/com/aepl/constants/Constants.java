package com.aepl.constants;

/**
 * @author Suraj_Bhalerao
 * @version 1.0
 * 
 * 
 * The `Constants` interface defines a collection of constant values used across the application.
 * 
 * <p>
 * This interface centralizes static, immutable values such as URLs, credentials, expected messages, 
 * and configurations to ensure consistency and reduce redundancy in the codebase.
 * </p>
 * 
 * <p>
 * Key Features:
 * <ul>
 *   <li>Centralized management of commonly used constants for easier maintenance.</li>
 *   <li>Defines configuration options like browser types and base URLs.</li>
 *   <li>Standardizes expected messages and error handling for validation.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Usage Example:
 * <pre>
 * {@code
 * String loginUrl = Constants.LOGIN_URL;
 * String expectedTitle = Constants.EXPECTED_TITLE;
 * }
 * </pre>
 * </p>
 */
public interface Constants {

    // Base URLs
    /**
     * The base URL for the application.
     * Used as the root for constructing other application-specific URLs.
     */
    String BASE_URL = "http://20.219.88.214:6102";

    /**
     * The URL for the dashboard page.
     * Represents the target URL after a successful login.
     */
    String DASHBOARD_URL = BASE_URL + "/dashboard";

    /**
     * The URL for the login page.
     * Used to navigate to the login interface of the application.
     */
    String LOGIN_URL = BASE_URL + "/login";

    // Browser Types
    /**
     * An array of supported browser types for the application.
     * Includes "chrome", "firefox", and "edge".
     */
    String[] BROWSER_TYPES = { "chrome", "firefox", "edge" };

    // Credentials
    /**
     * A valid username for authentication during testing.
     * Example: `suraj.bhalerao@accoladeelectronics.com`
     */
    String VALID_USERNAME = "suraj.bhalerao@accoladeelectronics.com";

    /**
     * A valid password for authentication during testing.
     * Example: `cqf9tnvl`
     */
    String VALID_PASSWORD = "cqf9tnvl";

    // Expected Titles
    /**
     * The expected title for the application interface.
     * Used for validating the page title in UI tests.
     */
    String EXPECTED_TITLE = "AEPL ATCU Diagnostic";

    // Error Messages
    /**
     * An array of error messages related to email validation.
     * Includes prompts for missing email or invalid email format.
     */
    String[] EMAIL_ERROR_MESSAGE = { 
        "Please Enter Email ID.", 
        "Please Enter Valid Email." 
    };

    /**
     * An array of error messages related to password validation.
     * Includes prompts for missing password or insufficient character length.
     */
    String[] PASSWORD_ERROR_MESSAGE = { 
        "Please Enter Password.", 
        "Please Enter Minimum 6 Characters." 
    };

    /**
     * An array of toast error messages displayed during login failure.
     * Includes messages for user not found or incorrect credentials.
     */
    String[] TOAST_ERROR_MESSAGE = { 
        "User Not Found", 
        "login Failed due to Incorrect email or password" 
    };
}
