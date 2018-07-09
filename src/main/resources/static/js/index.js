$('#add-tag-modal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let noteId = button.data('note-id');

    let modal = $(this);
    modal.find('#add-tag-note-id').val(noteId);
});

function addTag() {
    let name = $('#tag-name').val();
    let noteId = $('#add-tag-note-id').val();
    let url = `/addTag?noteId=${noteId}&name=${name}`;
    $.ajax({
        url: url,
        method: 'get',
        success: function (result) {
            document.location.reload();
        }
    });
}


$(document).keyup(function (e) {
    let name = $('#tag-name').val();
    if (e.keyCode === 13 && name.length > 0) {
        addTag();
    }
});

