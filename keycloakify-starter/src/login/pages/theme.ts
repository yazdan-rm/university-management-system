import { createTheme } from '@mui/material/styles';
import { faIR } from '@mui/material/locale'; // For Persian/Farsi support

const theme = createTheme({
    direction: 'rtl', // Set global direction
    typography: {
        fontFamily: 'Yekan, Arial, sans-serif', // Set your RTL font
    },
}, faIR); // Apply locale-specific settings

export default theme;