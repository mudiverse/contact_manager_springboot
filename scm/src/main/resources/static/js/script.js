console.log("Theme script loaded");

// Apply theme on page load
document.addEventListener("DOMContentLoaded", () => {
    let currentTheme = getTheme();
    changePageTheme(currentTheme);

    const changeThemeButton = document.querySelector("#theme_change_button");

    if (changeThemeButton) {
        changeThemeButton.addEventListener("click", () => {
            const newTheme = getTheme() === "dark" ? "light" : "dark";
            changePageTheme(newTheme);
        });
    }
});

// Save theme
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Read theme
function getTheme() {
    return localStorage.getItem("theme") || "light";
}

// Apply theme to <html>
function changePageTheme(theme) {
    setTheme(theme);

    const html = document.documentElement;
    html.classList.remove("light", "dark");
    html.classList.add(theme);

    // Update theme toggle icons
    const darkIcon = document.querySelector("#theme-toggle-dark-icon");
    const lightIcon = document.querySelector("#theme-toggle-light-icon");
    
    if (theme === "dark") {
        darkIcon.classList.add("hidden");
        lightIcon.classList.remove("hidden");
    } else {
        darkIcon.classList.remove("hidden");
        lightIcon.classList.add("hidden");
    }
}
