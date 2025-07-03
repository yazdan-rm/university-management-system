import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { useTheme } from "@mui/styles";

function ShortcutLink({ children, redirectUrl }) {
  const userTheme = useTheme();

  return (
    <Link
      to={redirectUrl}
      style={{
        backgroundColor: userTheme.palette.secondary.main,
      }}
      className=" inline-block h-[22rem] shadow-24 rounded-2xl overflow-hidden"
    >
      <motion.button
        whileHover={{ scale: 1.2 }}
        whileTap={{ scale: 0.8 }}
        className={`w-[15em] h-[15em] rounded-xl`}
      >
        {children}
      </motion.button>
    </Link>
  );
}

export default ShortcutLink;
