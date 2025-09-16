import js from "@eslint/js";
import globals from "globals";
import tseslint from "typescript-eslint";
import pluginReact from "eslint-plugin-react";
import prettier from "eslint-config-prettier"
import reactHooks from 'eslint-plugin-react-hooks';
import importPlugin from 'eslint-plugin-import';
import { defineConfig, globalIgnores } from 'eslint/config';

export default defineConfig([
  // {
  //   files: ["**/*.{js,mjs,cjs,mts,cts,jsx}"],
  //   plugins: { js },
  //   extends: ["js/recommended"],
  // },
  // {
  //   files: ["**/*.{js,mjs,cjs,ts,mts,cts,jsx,tsx}"],
  //   languageOptions: { globals: globals.browser },
  // },
  globalIgnores(["eslint.config.js"]),

  {
    name: "JavaScript + React Rules",
    files: ["**/*.{js,mjs,cjs,mts,cts,jsx}"],
    plugins: {
      react: pluginReact,
      "react-hooks": reactHooks,
      import: importPlugin,
    },
    extends:[
      js.configs.recommended,
    ],
    rules: {
      ...reactHooks.configs['recommended-latest'].rules,
      ...pluginReact.configs.recommended.rules,
      ...pluginReact.configs['jsx-runtime'].rules,
      ...importPlugin.configs.recommended.rules,
    },
    settings: {
      react: {
        version: 'detect',
      },
    },
  },
  {
    name: "TypeScript + React Rules",
    files: ['**/*.{tsx,ts}'],
    languageOptions: {
      parser: tseslint.parser,
      parserOptions: {
        project: [
          './tsconfig.app.json',
          './tsconfig.node.json'
        ],
        ecmaVersion: 'latest',
        sourceType: 'module',
        ecmaFeatures: {
          jsx: true,
          modules: true,
        },
      },
      globals: globals.browser,
    },
    plugins: {
      "typescript-eslint": tseslint,
      react: pluginReact,
      "react-hooks": reactHooks,
      import: importPlugin,
    },
    extends:[
      tseslint.configs.recommended,
      importPlugin.configs.typescript,
    ],
    rules: {
      ...reactHooks.configs['recommended-latest'].rules,
      ...pluginReact.configs.recommended.rules,
      ...pluginReact.configs['jsx-runtime'].rules,
      ...importPlugin.configs.recommended.rules,
    },
    settings: {
      react: {
        version: 'detect',
      },
      "import/resolver": {
        typescript: true,
        node: true,
      },

    },

  },
  prettier
  // tseslint.config(
  //   { files: ['**/*.{tsx,ts}'] },
  //   js.configs.recommended,
  // ),

]);
