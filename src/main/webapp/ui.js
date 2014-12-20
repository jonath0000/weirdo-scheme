var evaluate = function () {
    var code = document.getElementById('code').value;

	var evaluated = window.evalExpression(code);

    document.getElementById('output').innerHTML = evaluated;
    document.getElementById('environment').innerHTML = window.getEnvironment();
}


window.onload = function() {
    document.getElementById('evaluate').onclick = evaluate;
}
