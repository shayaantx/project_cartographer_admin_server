function showSuccessToast(message) {
  $.toast({
    text: message,
    position: 'top-center',
    bgColor: '#44bf75',
    stack: false
  });
}

function showErrorToast(message) {
  $.toast({
    text: message,
    position: 'top-center',
    bgColor: '#c23c3b',
    stack: false,
    hideAfter: 10000
  });
}