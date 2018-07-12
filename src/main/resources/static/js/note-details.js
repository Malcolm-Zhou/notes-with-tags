let allTags = $("button.tag");
let noteId = $("#noteId").val();

function drawFlowChart() {
    //flowchat 画流程图
    let divs = $('.flow');
    let i = 1;
    for (const div of divs) {

        $(div).attr('id', `flowchart${i}`)
        let chartCode = HTMLDecode($(div).html());
        let diagram = flowchart.parse(chartCode);
        $(div).html('');
        diagram.drawSVG(`flowchart${i}`);
        i++;
    }
}

//HTML反转义
/**
 * @return {string}
 */
function HTMLDecode(text) {
    let temp = document.createElement("div");
    temp.innerHTML = text;
    let output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}

//删除模式切换class，目的是改变tag颜色
$("#delete-tag-mode").change(function () {
    $.each(allTags, function (index, tag) {
        $(tag).toggleClass("btn-primary");
        $(tag).toggleClass("btn-danger");
    });
});

//删除模式时ajax删除tag
$.each(allTags, function (index, tag) {
    $(tag).click(function () {
        let tagId = $(this).attr("tag-id");
        let inDeleteMode = $(this).hasClass("btn-danger");
        if (inDeleteMode) {
            let url = "/deleteTag?noteId=" + noteId + "&tagId=" + tagId;
            $.ajax({
                url: url,
                method: 'get',
                success: function (result) {
                    console.log(result);
                    $(tag).remove();
                }
            });
        }
    })
});

//自动刷新Markdown预览
$('#content').keyup(function () {
    let content = $(this).val();
    $.ajax({
        url: "/refreshMD",
        method: 'post',
        data: {'content': content},
        success: function (result) {
            $('#mdHTML').html(result);
            drawFlowChart();
        }
    });
});

//文本框高度自适应
let textarea = document.getElementById('content');
textarea.style.height = 'auto';
textarea.style.height = textarea.scrollHeight + "px";

textarea.onkeyup = function () {
    this.style.height = 'auto';
    this.style.height = this.scrollHeight + "px";
};

drawFlowChart();