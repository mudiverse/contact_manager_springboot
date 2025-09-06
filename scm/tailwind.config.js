/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",   // Thymeleaf templates
    "./src/main/resources/static/**/*.js",        // JS toggle files
    "./node_modules/flowbite/**/*.js"             // Flowbite
  ],
  darkMode: "class",   // enable dark mode via `class` strategy
  theme: {
    extend: {},
  },
  plugins: [
    require("flowbite/plugin")  // flowbite plugin
  ],
  safelist: [
    "dark",
    "dark:bg-black",
    "dark:text-white",
    "dark:bg-gray-900",
    "dark:text-gray-100"
  ]
};
