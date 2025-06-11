import React from "react";
import volcanoUrl from "./assets/icons/volcano.svg?url";

export default function TestIcon() {
  console.log("volcanoUrl:", volcanoUrl);
  return (
    <div style={{ textAlign: "center", marginTop: 50 }}>
      <p>¿Se ve este SVG de volcanes?</p>
      <img
        src={volcanoUrl}
        width={64}
        height={64}
        alt="Test Volcano Icon"
        style={{ border: "2px solid red" }}
      />
    </div>
  );
}
