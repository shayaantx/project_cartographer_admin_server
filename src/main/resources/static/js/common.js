function lookupUserById(id) {
    window.location.replace(window.location.protocol + "user?id=" + id);
}

function lookupUserByUsername(username) {
    window.location.replace(window.location.protocol + "user?username=" + username);
}

function banUserById(id) {
    window.location.replace(window.location.protocol + "banUser?id=" + id);
}

function unbanUserById(id) {
    window.location.replace(window.location.protocol + "unbanUser?id=" + id);
}

function initCommonJs() {
    $(document).ready(function () {
        $("#userTable tr").click(function(){
            var id = $(this).find('td:first').html();
            lookupUserById(id);
        });
        $("#findButton").click(function() {
            var id = $('#idToFind').val();
            var username = $('#usernameToFind').val();
            var email = $('#emailToFind').val();
            if (!id && !username && !email) {
                alert("Must provide a id or username or email");
            }
            if (id) {
                lookupUserById(id);
                return;
            }
            if (username) {
                lookupUserByUsername(username);
            }
        });

        $("#banUser").click(function() {
            var id = $('#id').val();
            banUserById(id);
        });

        $("#unbanUser").click(function() {
            var id = $('#id').val();
            unbanUserById(id);
        });
    });
}

function onUserSelect() {
}