function lookupUserById(id) {
  window.location.replace(window.location.protocol + "user?id=" + id);
}

function lookupUserByUsername(username) {
  window.location.replace(window.location.protocol + "user?username=" + encodeURIComponent(username));
}