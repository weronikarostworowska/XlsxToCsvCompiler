<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome!</h1>
<p>You have successfully logged in.!!!</p>
<form action="upload" method="post" enctype="multipart/form-data" onsubmit="return validateFile()">
    <button type="button" onclick="document.getElementById('file-input').click();">Wybierz plik</button>
    <br><br>
    <span id="file-name"></span>
    <input type="file" id="file-input" name="file" style="display:none;" onchange="updateFileName(this);" accept=".xls" />
    <script>
        function updateFileName(input) {
            if (input.files.length > 0) {
                var fileName = input.files[0].name;
                document.getElementById('file-name').textContent = fileName;
                document.getElementById('selected-file').value = input.files[0].path;
            }
        }

        function validateFile() {
            var fileInput = document.getElementById('file-input');
            var fileName = fileInput.value;
            if (fileName.length === 0) {
                alert("Prosze wybrac plik w formacie xls.");
                return false;
            }
            if (!fileName.toLowerCase().endsWith(".xls")) {
                alert("Prosze wybrac plik w formacie xls.");
                return false;
            }
            return true;
        }
    </script>
    <br><br>
    <button type="submit">Convert</button>
</form>

<div id="file-selection">
    <input type="hidden" id="selected-file" name="selected-file" value="" />
</div>

</body>
</html>
