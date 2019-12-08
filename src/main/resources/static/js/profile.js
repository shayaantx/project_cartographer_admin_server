function changePassword() {
    const postData = JSON.stringify({
        newPassword: $('#newPassword').val()
    });

    post(
        "changePassword",
        postData,
        function(result) {
            showSuccessToast("Successfully changed password, next time you login you can use it!");
        }
    );
}

function initProfiles() {

    $(document).ready(function () {
        $("#changePassword").click(function() {
            changePassword();
        });
    });
}