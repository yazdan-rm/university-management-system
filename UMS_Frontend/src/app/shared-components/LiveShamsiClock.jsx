import { useEffect, useState } from "react";
import { getCurrentDateTimeToShamsi } from "../utils/UmsUtils.js";

function LiveShamsiClock({ className }) {
  const [now, setNow] = useState(getCurrentDateTimeToShamsi());

  useEffect(() => {
    const interval = setInterval(() => {
      setNow(getCurrentDateTimeToShamsi());
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  return <span className={className}>{now}</span>;
}

export default LiveShamsiClock;
