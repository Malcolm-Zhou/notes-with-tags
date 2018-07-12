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
function deleteNote() {
    let noteId = $('#note-id-delete').val();
    let url = `/deleteNote?id=${noteId}`;
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

$('#confirm-delete-modal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let id = button.data('note-id');
    let title = button.data('note-title');
    let modal = $(this);
    modal.find('#note-title-delete').text(`要删除 ${title} 吗？`);
    modal.find('#note-id-delete').val(id);
});

