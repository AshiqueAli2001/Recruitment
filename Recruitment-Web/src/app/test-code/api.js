const express = require("express");
const app = express();
const bodyp = require("body-parser");
const fetch = require("node-fetch");
const cors = require("cors");

app.use(bodyp.json());
app.use(cors());

// Serve CodeMirror assets
app.use("/src/app/test-code/codemirror-5.65.16", express.static("D:/proj/trunk/Recruitment-Web/src/app/test-code/codemirror-5.65.16"));

// Serve HTML file
app.get("/", function (req, res) {
  res.sendFile("D:/proj/trunk/Recruitment-Web/src/app/test-code/test-code.component.html");
});

app.post("/compile", async function (req, res) {
  const code = req.body.code;
  const input = req.body.input;
  const lang = req.body.lang;

  try {
    const jdoodleResponse = await fetch("https://api.jdoodle.com/v1/execute", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        clientId: "c621c7ac7c350fc51cb8d9be50bcd6b5",
        clientSecret: "7d426c0d64e169a19fa8cc62d015cda23bfd480b62052d1a93aa966c26fbf57c",
        script: code,
        stdin: input,
        language: lang.toLowerCase(),
      }),
    });

    const jdoodleData = await jdoodleResponse.json();
    res.send({ output: jdoodleData.output });
  } catch (error) {
    console.error("Error during JDoodle compilation:", error);
    res.status(500).send({ output: "Error during JDoodle compilation" });
  }
});

const port = 8000;
app.listen(port, () => {
  console.log(`Server is listening on port ${port}`);
});
