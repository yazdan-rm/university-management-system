import { createRoot } from "react-dom/client";
import { StrictMode } from "react";
import { KcPage } from "./kc.gen";
import { ThemeProvider } from "@mui/material/styles";
import theme from "./login/pages/theme.ts";

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        {!window.kcContext ? (
            <h1>No Keycloak Context</h1>
        ) : (
            <ThemeProvider theme={theme}>
                <KcPage kcContext={window.kcContext} />
            </ThemeProvider>
        )}
    </StrictMode>
);
