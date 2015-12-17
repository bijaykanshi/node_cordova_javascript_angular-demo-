var basicKeyboard = {
    settings: {
        buttonClass: "button",
        onclick: "basicKeyboard.keyTap();",
        keyClass: "key",
        text: {
            close: "close"
        }
    },
    keyTap: function (value) {
        var field = basicKeyboard.field,
            startPos,
            endPos;
        if (field.selectionStart >= 0) {
            startPos = field.selectionStart;
            endPos = field.selectionEnd;
            field.value = field.value.substring(0, startPos) +
                String.fromCharCode(value) +
                field.value.substring(endPos, field.value.length);
            field.focus();
            field.selectionStart = startPos + 1;
            field.selectionEnd = endPos + 1;
        }
    },
    initialize: function (elem) {
        basicKeyboard.keyboardLayout = elem;
        basicKeyboard.generateKeyboard();
    },
    generateKeyboard: function () {
        var onclick = "",
            text = "",
            div = "";
        div += "<div id=\"keyboard\"><div id=\"keyboardHeader\"></div>";
        div += "<div id=\"keyboardCapitalLetter\">";
        basicKeyboard.defaultKeyboard.capitalLetter.forEach(function (key) {
            generate(key);
        });
        div += "</div>";
        function generate(key) {
            onclick = key.onclick === undefined ? basicKeyboard.settings.onclick.replace("()", "(" + key.value + ")") : key.onclick;
            text = (key.isChar !== undefined || key.isChar === false) ? key.value : String.fromCharCode(key.value);
            div += "<button class='basicButton' onclick=\"" + onclick + "\"><div class='key'>" + text + "</div></button>";
            onclick = "";
            text = "";
        }
        document.getElementById(basicKeyboard.keyboardLayout).innerHTML = div;
    },
    defaultKeyboard: {
        capitalLetter:
            [
                { value: 81 }, { value: 87 }, { value: 69 }, { value: 82 }, { value: 84 }, { value: 89 },
                { value: 85 }, { value: 73 }, { value: 79 }, { value: 80 },
                { value: 65, buttonClass: "button button_a" }, { value: 83 }, { value: 68 }, { value: 70 },
                { value: 71 }, { value: 72 }, { value: 74 }, { value: 75 }, { value: 76 },
                { value: 90 }, { value: 88 }, { value: 67 }, { value: 86 }, { value: 66 }, { value: 78 },
                { value: 77 }, { value: 44 }, { value: 46 }, { value: 39, buttonClass: "button button_quote" },
                { value: 32, buttonClass: "button button_space" }
            ]
    }
};