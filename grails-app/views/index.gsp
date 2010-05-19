<html>
    <head>
		<meta name="layout" content="main" />
    </head>
    <body>
      <h1><g:message code="site.page.index.intro0" /></h1>
      <p><g:message code="site.page.index.intro1" args="[message(code:'site.title')]" /></p>
      <hr />
      <h2 id="upload_anchor">早速作る</h2>
      <p>　最初から証明写真のサイズのつもりで撮影するのではなく、余白を十分に取り、正面を向いた顔が写っている写真を用意してください。背景や格好については<a href="http://www.silvia777.com/baito/pic.html">こちらなど</a>を参考にしてください。</p>
      <p><img alt="face01" src="${resource(dir:'images',file:'face01.jpg')}" /><img alt="face02" src="${resource(dir:'images',file:'face02.jpg')}" /></p>
      <p>　次に、用意した顔写真のファイルを選択し、「次へ」ボタンをクリックしてください。</p>
      <g:form controller="photo" action="save" method="post"  enctype="multipart/form-data">
        <div class="dialog">
          <table class="form" summary="upload form">
            <tbody>
              <tr class="prop">
                <td valign="top" class="name">
                  <label for="data"><g:message code="photo.data.label" default="Data" /></label>
                </td>
                <td valign="top" class="value">
                  <input type="file" id="data" name="data" />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.paginate.next')}" /></span>
        </div>
      </g:form>
      <hr />
      <h2>作るにはどうすればいいの？</h2>
      <h3>必要なもの</h3>
      <ul>
        <li>プリンター</li>
        <li>写真印刷用の用紙　たとえば、<a href="http://www.amazon.co.jp/gp/product/B000JCEQ9K?ie=UTF8&amp;tag=ledybaddojp-22&amp;linkCode=as2&amp;camp=247&amp;creative=7399&amp;creativeASIN=B000JCEQ9K">こういうの</a></li>
        <li>デジカメ（300万画素以上を強く推奨）</li>
      </ul>
      <p>　次に、以下のソフトがインストールされていることも必要です。</p>
      <ul>
        <li><a href="http://get.adobe.com/jp/reader/">Adobe Acrobat Reader</a></li>
      </ul>
      <h3>証明写真をつくるまでのおおまかな手順</h3>
      <p>　とりあえず<a href="#upload_anchor">上記のフォーム</a>から画像をアップロードしてしまえば、画面の指示に従うだけですが、おおまかにはこんな感じです。</p>
      <ol>
        <li>デジカメを使用して、あなたの顔写真を撮影してください。
        <ul>
          <li>できるだけ背景は白にしてください。</li>
          <li>顔は写真の中央に、十分に余白を取って撮影してください。</li>
        </ul></li>
        <li><a href="#upload_anchor">上記のフォーム</a>を用いてアップロードしてください。</li>
        <li>アップロード後のページ内の指示に従って、以下を設定してください。
        <ul>
          <li>顔の位置</li>
          <li>どのサイズの顔写真が、どれほど必要か</li>
          <li>印刷に用いる紙のサイズはどれほどか</li>
        </ul></li>
        <li>以上のデータを反映させたPDFファイルが作成されます。これを写真印刷用紙を使ってプリンターで印刷すればできあがりです。</li>
      </ol>
      <hr />
      <h2>その他の注意事項</h2>
      <p>　このサイトは、<a href="http://ledyba.ddo.jp/">ψ（プサイ）</a>が、大学受験・入学時の証明写真代を浮かすために作成したものを、一般の使用に耐えられるように改善した上で公開、運営しているものです。</p>
      <p>　受験はもちろん、免許証とパスポートでも使用できると思いますが、就職活動ではあまり使わないほうが良いかもしれません。</p>
      <p>　このサービスを利用して不利益やトラブルに見舞われても残念ですがψ（プサイ）は責任を負うことが出来ません。自己責任でのご利用をお願いします。</p>
      <p>　その他のお問い合わせは<a href="http://mailhide.recaptcha.net/d?k=01_tNskYQiCbtLkVyaGlX-aQ==&amp;c=aioQurMJnNIUSw1X_rnWfTMfN17NMI5a6WhP_2BCwWw=">ψ（プサイ）のメールアドレス</a>まで。メールアドレスは<a href="http://recaptcha.net/">recaptcha</a>を使用して保護しております。リンク先のページの指示に従ってください（簡単です）。</p>
    </body>
</html>