
<%@ page import="app.Photo" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <g:javascript library="scriptaculous" />
    <g:javascript src="pdf_create.js" />
    <title>印刷設定</title>
  </head>
  <body>
    <p>あなたの顔写真ファイルのアップロードが完了しました。次は、印刷するための設定を行いましょう。</p>
    <g:form name="pdf_create_form" url="[action:'create',controller:'pdf']" target="_blank">
    <input id="form_photo_id" name="photo_id" type="hidden" value="${photoInstance.id}" />
    <input id="form_photo_passid" name="photo_passid" type="hidden" value="${photoInstance.passId}" />
    <h2>顔の中央のラインを設定してください</h2>
      <div id="photo_wrapper_appended_row">Photo</div>
      <input id="photo_center" name="photo_center" type="hidden" />
      <script type="text/javascript">
        var row_manager = new PhotoManager('row','${createLink(action:'thumb',controller:'photo',id:photoInstance.id,params:[passid:photoInstance.passId])}',${photoInstance.thumbWidth},${photoInstance.thumbHeight},'${resource(dir:'images',file:'face_center.jpg')}');
        row_manager.appendTo('photo_row','photo_selector_row','photo_selector_wrapper_row','photo_wrapper_appended_row');
        row_manager.setSlider('photo_center',0.5);
      </script>
    <h2>あたまのてっぺんのラインを設定してください</h2>
      <div id="photo_wrapper_appended_col_top">Photo</div>
      <input id="photo_top" name="photo_top" type="hidden" />
      <script type="text/javascript">
        var col_manager_top = new PhotoManager('col','${createLink(action:'thumb',controller:'photo',id:photoInstance.id,params:[passid:photoInstance.passId])}',${photoInstance.thumbWidth},${photoInstance.thumbHeight},'${resource(dir:'images',file:'face_top.jpg')}');
        col_manager_top.appendTo('photo_col_top','photo_selector_col_top','photo_selector_wrapper_col_top','photo_wrapper_appended_col_top');
        col_manager_top.setSlider('photo_top',0.2);
      </script>
    <h2>アゴのラインを設定してください</h2>
      <div id="photo_wrapper_appended_col_bottom">Photo</div>
      <input id="photo_bottom" name="photo_bottom" type="hidden" />
      <script type="text/javascript">
        var col_manager_bottom = new PhotoManager('col','${createLink(action:'thumb',controller:'photo',id:photoInstance.id,params:[passid:photoInstance.passId])}',${photoInstance.thumbWidth},${photoInstance.thumbHeight},'${resource(dir:'images',file:'face_bottom.jpg')}');
        col_manager_bottom.appendTo('photo_col_bottom','photo_selector_col_bottom','photo_selector_wrapper_col_bottom','photo_wrapper_appended_col_bottom');
        col_manager_bottom.setSlider('photo_bottom',0.8);
      </script>
    <h2>印刷用紙のサイズはどれですか？</h2>
      <g:pdf_document_size_lists name="document_size" class_name="document_size_list" />
    <h2>必要な証明写真のサイズと数はどれくらいですか？</h2>
    <p>　最低でも指定枚数は印刷されます。横のスペースがあまった場合、さらに印刷されます。</p>
    <p>　証明写真のサイズに関しては<a target="_blank" href="http://www.mpstudio.co.jp/idphoto/size/">こちらなど</a>を参考にしてください。</p>
    <div id="photo_size_list_wrapper">List</div>
      <script type="text/javascript">
      var photo_size_list_manager = new PhotoSizeListManager('photo_size','photo_size_list_wrapper',"${message(code:'pdf.photo.size.width')}","${message(code:'pdf.photo.size.height')}","${message(code:'pdf.photo.size.add')}","${message(code:'pdf.photo.size.count')}");
      </script>
    <h2>設定が完了しました</h2>
    <p>以下のボタンを押して作成してください。別のウインドウが開きます。気に入らない場合、何度でもやり直す事ができます。</p>
      <p>
        <g:submitButton name="submit" value="${message(code: 'pdf.create')}" />
      </p>
    </g:form>
    <hr />
  <script type="text/javascript">
  google_ad_client = "pub-3121031347596821";
  google_ad_slot = "9025460473";
  google_ad_width = 468;
  google_ad_height = 60;
  </script>
  <script type="text/javascript"
  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
  </script>
    <hr />
    <h2>印刷ガイド</h2>
    <p>　PDFファイルが表示されたら、以下の手順に従って印刷してください。</p>
    <h3>紙をセットし、プリンタの電源を入れてください。</h3>
    <p>　印刷用紙をプリンタにセットし、プリンタのUSBケーブルをPCに接続し、プリンタの電源を入れてください。</p>
    <h3>印刷ボタンを押してください</h3>
    <img alt="step 1" src="${resource(dir:'images',file:'step1.jpg')}" />
    <p>　左上のほうにある印刷ボタンを押してください。</p>
    <h3>プリンタを設定</h3>
    <img alt="step 2" src="${resource(dir:'images',file:'step2.jpg')}" />
    <p>　上のほうのプリンタ設定をご使用のものにあわせて変更してください。特別に写真向けの設定が出来るプリンタもあるようです。フチなし印刷する必要はありません。詳しくはお使いのプリンタのマニュアルをご参照ください。</p>
    <h3>拡大率の設定</h3>
    <img alt="step 3-1" src="${resource(dir:'images',file:'step3-1.jpg')}" />
    <p>　次は真ん中のほうにある「ページ設定」に注目してください。</p>
    <img alt="step 3-2" src="${resource(dir:'images',file:'step3-2.jpg')}" />
    <p>　このように、「ページの拡大 / 縮小」は「なし」に設定してください。こう設定しないと、写真が意図したものより小さく印刷されてしまいます。</p>
    <h3>印刷</h3>
    <img alt="step 4" src="${resource(dir:'images',file:'step4.jpg')}" />
    <p>　以上で終了です。「OK」ボタンを押すと、印刷が開始されます。</p>
</body>
</html>
