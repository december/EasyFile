<TeXmacs|1.99.1>

<style|<tuple|article|chinese>>

<\body>
  <doc-data|<doc-title|adapter\<#5305\>\<#8BBE\>\<#8BA1\>\<#6587\>\<#6863\>>|<doc-author|<author-data|<author-name|>|<\author-affiliation>
    \<#6881\>\<#4FCA\>\<#90A6\>

    Melancholy
  </author-affiliation>>>>

  <section|\<#5305\>\<#7684\>\<#5B9A\>\<#4F4D\>>

  \;

  adapter\<#5305\>\<#91CC\>\<#9762\>\<#5B9E\>\<#73B0\>\<#4E86\>3\<#4E2A\>\<#7C7B\>\<#FF0C\>\<#8D1F\>\<#8D23\>\<#652F\>\<#6301\>\<#524D\>\<#7AEF\>\<#548C\>\<#540E\>\<#7AEF\>\<#7684\>\<#8FDE\>\<#63A5\>\<#5DE5\>\<#4F5C\>\<#FF0C\>\<#5305\>\<#62EC\>\<#5730\>\<#5740\>\<#7684\>\<#8F6C\>\<#6362\>\<#FF0C\>\<#4E0B\>\<#8F7D\>\<#4E0A\>\<#4F20\>\<#7EBF\>\<#7A0B\>\<#7684\>\<#63A7\>\<#5236\>\<#FF0C\>\<#6807\>\<#7B7E\>\<#7CFB\>\<#7EDF\>\<#3001\>\<#6743\>\<#9650\>\<#7CFB\>\<#7EDF\>\<#3001\>\<#7F51\>\<#76D8\>\<#7CFB\>\<#7EDF\>\<#7684\>\<#5404\>\<#4E2A\>\<#63A5\>\<#53E3\>\<#7684\>\<#8FDE\>\<#63A5\>\<#7B49\>\<#3002\>

  <section|FTPAdapter>

  \;

  \<#8FD9\>\<#4E2A\>\<#7C7B\>\<#4E3A\>\<#4E3B\>\<#8981\>\<#7684\>\<#63A7\>\<#5236\>\<#7C7B\>\<#FF0C\>\<#628A\>\<#57FA\>\<#672C\>\<#7684\>\<#529F\>\<#80FD\>\<#90FD\>\<#5B9E\>\<#73B0\>\<#4E86\>\<#FF0C\>\<#5176\>\<#4ED6\>\<#4E24\>\<#4E2A\>\<#7C7B\>\<#4F5C\>\<#4E3A\>\<#5B83\>\<#7684\>\<#6210\>\<#5458\>\<#88AB\>\<#5B83\>\<#4F7F\>\<#7528\>\<#3002\>

  <subsection|\<#6210\>\<#5458\>\<#53D8\>\<#91CF\>>

  \;

  <big-table|<block|<tformat|<table|<row|<cell|\<#53D8\>\<#91CF\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|WPMODE>|<cell|\<#7F51\>\<#76D8\>\<#6A21\>\<#5F0F\>\<#4EE3\>\<#7801\>>>|<row|<cell|FTPMODE>|<cell|ftp\<#6A21\>\<#5F0F\>\<#4EE3\>\<#7801\>>>|<row|<cell|USEENCODER>|<cell|\<#662F\>\<#5426\>\<#4F7F\>\<#7528\>\<#89E3\>\<#7801\>\<#5668\>\<#8BFB\>\<#5165\>\<#8BC1\>\<#4E66\>>>|<row|<cell|mainConnect>|<cell|\<#4E3B\>\<#754C\>\<#9762\>\<#5BF9\>ftp\<#670D\>\<#52A1\>\<#5668\>\<#7684\>\<#8FDE\>\<#63A5\>\<#5668\>>>|<row|<cell|baiduConnection>|<cell|\<#767E\>\<#5EA6\>\<#7F51\>\<#76D8\>\<#7684\>\<#8FDE\>\<#63A5\>\<#5668\>>>|<row|<cell|curPos>|<cell|\<#672C\>\<#5730\>\<#5F53\>\<#524D\>\<#76EE\>\<#5F55\>>>|<row|<cell|credential>|<cell|\<#5F53\>\<#524D\>\<#8BC1\>\<#4E66\>\<#76EE\>\<#5F55\>>>|<row|<cell|processManager>|<cell|\<#4EFB\>\<#52A1\>\<#7BA1\>\<#7406\>\<#5668\>>>|<row|<cell|server>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#5730\>\<#5740\>>>|<row|<cell|userName>|<cell|\<#7528\>\<#6237\>\<#540D\>\<#5B57\>>>|<row|<cell|password>|<cell|\<#7528\>\<#6237\>\<#5BC6\>\<#7801\>>>|<row|<cell|defaultCredential>|<cell|\<#9ED8\>\<#8BA4\>\<#8BC1\>\<#4E66\>\<#76EE\>\<#5F55\>>>|<row|<cell|permission>|<cell|\<#6743\>\<#9650\>\<#7B49\>\<#7EA7\>>>|<row|<cell|onOurFTP>|<cell|\<#662F\>\<#5426\>\<#5728\>\<#6211\>\<#4EEC\>\<#7684\>\<#670D\>\<#52A1\>\<#5668\>\<#4E0A\>>>|<row|<cell|haveLoggedIn>|<cell|\<#662F\>\<#5426\>\<#5DF2\>\<#7ECF\>\<#767B\>\<#5F55\>>>|<row|<cell|labelManager>|<cell|\<#6807\>\<#7B7E\>\<#7CFB\>\<#7EDF\>\<#7BA1\>\<#7406\>\<#5668\>>>|<row|<cell|newmainConnect>|<cell|\<#65B0\>\<#8FDE\>\<#63A5\>\<#5668\>\<#FF0C\>\<#7528\>\<#4E8E\>\<#7F13\>\<#51B2\>\<#FF0C\>\<#4E0B\>\<#540C\>>>|<row|<cell|newuserName>|<cell|\<#65B0\>\<#7528\>\<#6237\>\<#540D\>\<#5B57\>>>|<row|<cell|newpassword>|<cell|\<#65B0\>\<#7528\>\<#6237\>\<#5BC6\>\<#7801\>>>|<row|<cell|newpermission>|<cell|\<#65B0\>\<#6743\>\<#9650\>\<#7B49\>\<#7EA7\>>>|<row|<cell|newserver>|<cell|\<#65B0\>\<#670D\>\<#52A1\>\<#5668\>\<#540D\>\<#5B57\>>>|<row|<cell|curMode>|<cell|\<#5F53\>\<#524D\>\<#6A21\>\<#5F0F\>>>|<row|<cell|adapter>|<cell|FTPAdapter\<#7684\>\<#552F\>\<#4E00\>\<#5B9E\>\<#4F8B\>>>>>>|>

  <subsection|\<#6210\>\<#5458\>\<#51FD\>\<#6570\>>

  <\big-table>
    <block|<tformat|<table|<row|<cell|\<#51FD\>\<#6570\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|getInstance>|<cell|\<#8FD4\>\<#56DE\>\<#5355\>\<#4EF6\>>>|<row|<cell|FTPAdapter>|<cell|\<#5355\>\<#4EF6\>\<#7684\>\<#6784\>\<#9020\>\<#51FD\>\<#6570\>>>|<row|<cell|getPermission>|<cell|\<#83B7\>\<#5F97\>\<#7528\>\<#6237\>\<#7684\>\<#6743\>\<#9650\>\<#7B49\>\<#7EA7\>>>|<row|<cell|connection>|<cell|\<#5C1D\>\<#8BD5\>\<#8FDE\>\<#63A5\>ftp\<#670D\>\<#52A1\>\<#5668\>>>|<row|<cell|loadCredential>|<cell|\<#5BFC\>\<#5165\>\<#8BC1\>\<#4E66\>\<#3002\>\<#8F93\>\<#5165\>\<#53EF\>\<#4EE5\>\<#662F\>\<#7A7A\>\<#4E32\>\<#FF0C\>\<#8868\>\<#793A\>\<#5BFC\>\<#5165\>\<#9ED8\>\<#8BA4\>\<#8BC1\>\<#4E66\>\<#3002\>>>|<row|<cell|logIn>|<cell|\<#7528\>\<#6237\>\<#767B\>\<#5F55\>>>|<row|<cell|logOut>|<cell|\<#7528\>\<#6237\>\<#767B\>\<#51FA\>>>|<row|<cell|getCurrentFTPPath>|<cell|\<#5F97\>\<#5230\>\<#5F53\>\<#524D\>ftp\<#8DEF\>\<#5F84\>\<#3002\>>>|<row|<cell|getCurrentLocalPath>|<cell|\<#5F97\>\<#5230\>\<#5F53\>\<#524D\>\<#672C\>\<#5730\>\<#8DEF\>\<#5F84\>>>|<row|<cell|changetoParentFTPDirectory>|<cell|\<#524D\>\<#5F80\>ftp\<#7684\>\<#7236\>\<#76EE\>\<#5F55\>>>|<row|<cell|changetoParentLocalDirectory>|<cell|\<#524D\>\<#5F80\>\<#672C\>\<#5730\>\<#7684\>\<#7236\>\<#76EE\>\<#5F55\>>>|<row|<cell|changeWorkingFTPDirectory>|<cell|\<#524D\>\<#5F80\>\<#7279\>\<#5B9A\>\<#7684\>ftp\<#76EE\>\<#5F55\>>>|<row|<cell|removeTitle>|<cell|\<#628A\>ftp\<#8DEF\>\<#5F84\>\<#7684\>\<#4E0D\>\<#5FC5\>\<#8981\>\<#7684\>\<#524D\>\<#7F00\>\<#53BB\>\<#6389\>>>|<row|<cell|changeWorkingLocalDirectory>|<cell|\<#524D\>\<#5F80\>\<#7279\>\<#5B9A\>\<#7684\>\<#672C\>\<#5730\>\<#76EE\>\<#5F55\>>>|<row|<cell|showServerFileList>|<cell|\<#663E\>\<#793A\>\<#5F53\>\<#524D\>ftp\<#76EE\>\<#5F55\>\<#4E0B\>\<#7684\>\<#6587\>\<#4EF6\>\<#5217\>\<#8868\>>>|<row|<cell|showLocalFileList>|<cell|\<#663E\>\<#793A\>\<#5F53\>\<#524D\>\<#672C\>\<#5730\>\<#76EE\>\<#5F55\>\<#4E0B\>\<#7684\>\<#6587\>\<#4EF6\>\<#5217\>\<#8868\>>>|<row|<cell|newDownloadProcess>|<cell|\<#65B0\>\<#5EFA\>\<#6216\>\<#7EE7\>\<#7EED\>\<#4E0B\>\<#8F7D\>\<#4EFB\>\<#52A1\>>>|<row|<cell|newUploadProcess>|<cell|\<#65B0\>\<#5EFA\>\<#6216\>\<#7EE7\>\<#7EED\>\<#4E0A\>\<#4F20\>\<#4EFB\>\<#52A1\>>>|<row|<cell|renameServerFile>|<cell|\<#5BF9\>ftp\<#670D\>\<#52A1\>\<#5668\>\<#7684\>\<#6587\>\<#4EF6\>\<#8FDB\>\<#884C\>\<#91CD\>\<#547D\>\<#540D\>>>|<row|<cell|removeServerDirectory>|<cell|\<#628A\>\<#4E00\>\<#4E2A\>\<#7A7A\>\<#6587\>\<#4EF6\>\<#5939\>\<#5728\>ftp\<#670D\>\<#52A1\>\<#5668\>\<#76EE\>\<#5F55\>\<#4E2D\>\<#5220\>\<#6389\>>>|<row|<cell|makeServerDirectory>|<cell|\<#5728\>ftp\<#6240\>\<#6C42\>\<#76EE\>\<#5F55\>\<#4E2D\>\<#65B0\>\<#5EFA\>\<#4E00\>\<#4E2A\>\<#7A7A\>\<#6587\>\<#4EF6\>\<#5939\>>>|<row|<cell|fileExistsFTP>|<cell|\<#8BE2\>\<#95EE\>ftp\<#670D\>\<#52A1\>\<#5668\>\<#4E0A\>\<#67D0\>\<#6587\>\<#4EF6\>\<#662F\>\<#5426\>\<#5B58\>\<#5728\>>>|<row|<cell|fileExistsLocal>|<cell|\<#8BE2\>\<#95EE\>\<#672C\>\<#5730\>\<#67D0\>\<#6587\>\<#4EF6\>\<#662F\>\<#5426\>\<#5B58\>\<#5728\>>>|<row|<cell|deleteServerFile>|<cell|\<#5220\>\<#9664\>ftp\<#670D\>\<#52A1\>\<#5668\>\<#7684\>\<#67D0\>\<#6587\>\<#4EF6\>>>|<row|<cell|cutProcess>|<cell|\<#6682\>\<#505C\>\<#6216\>\<#505C\>\<#6B62\>\<#67D0\>\<#4EFB\>\<#52A1\>\V\V\<#7528\>\<#4E8E\>\<#4E0A\>\<#4F20\>\<#6216\>\<#4E0B\>\<#8F7D\>\<#4EFB\>\<#52A1\>>>|<row|<cell|getAccomplishRate>|<cell|\<#5F97\>\<#5230\>\<#4EFB\>\<#52A1\>\<#5B8C\>\<#6210\>\<#767E\>\<#5206\>\<#6BD4\>>>|<row|<cell|isConnection>|<cell|\<#5224\>\<#65AD\>\<#5F53\>\<#524D\>\<#662F\>\<#5426\>\<#6B63\>\<#5728\>\<#8FDE\>\<#63A5\>>>|<row|<cell|haveLoggedIn>|<cell|\<#5224\>\<#65AD\>\<#5F53\>\<#524D\>\<#662F\>\<#5426\>\<#5DF2\>\<#767B\>\<#5F55\>>>|<row|<cell|addTag>|<cell|\<#5BF9\>\<#6587\>\<#4EF6\>\<#589E\>\<#52A0\>\<#6807\>\<#7B7E\>>>|<row|<cell|delTag>|<cell|\<#5220\>\<#9664\>\<#6807\>\<#7B7E\>>>|<row|<cell|showTag>|<cell|\<#663E\>\<#793A\>\<#6587\>\<#4EF6\>\<#7684\>\<#6807\>\<#7B7E\>\<#5217\>\<#8868\>>>|<row|<cell|getParentDirectory>|<cell|\<#5F97\>\<#5230\>\<#7279\>\<#5B9A\>\<#8DEF\>\<#5F84\>\<#7684\>\<#7236\>\<#76EE\>\<#5F55\>\<#8DEF\>\<#5F84\>>>|<row|<cell|getFileList>|<cell|\<#663E\>\<#793A\>\<#7279\>\<#5B9A\>\<#8DEF\>\<#5F84\>\<#7684\>\<#6587\>\<#4EF6\>\<#5217\>\<#8868\>>>|<row|<cell|getFile>|<cell|\<#5F97\>\<#5230\>\<#67D0\>\<#8DEF\>\<#5F84\>\<#7684\>\<#6587\>\<#4EF6\>>>|<row|<cell|getCurServer>|<cell|\<#5F97\>\<#5230\>\<#5F53\>\<#524D\>\<#670D\>\<#52A1\>\<#5668\>\<#540D\>\<#5B57\>>>|<row|<cell|showAllKey>|<cell|\<#663E\>\<#793A\>\<#6240\>\<#6709\>\<#79CD\>\<#7C7B\>\<#7684\>\<#6807\>\<#7B7E\>>>|<row|<cell|addKey>|<cell|\<#589E\>\<#52A0\>\<#4E00\>\<#79CD\>\<#65B0\>\<#7684\>\<#6807\>\<#7B7E\>>>|<row|<cell|delKey>|<cell|\<#5220\>\<#9664\>\<#4E00\>\<#79CD\>\<#6807\>\<#7B7E\>>>|<row|<cell|changeLabelByKey>|<cell|\<#4FEE\>\<#6539\>\<#4E00\>\<#79CD\>\<#6807\>\<#7B7E\>\<#7684\>\<#5C5E\>\<#6027\>>>|<row|<cell|changeLabelOfFile>|<cell|\<#4FEE\>\<#6539\>\<#6587\>\<#4EF6\>\<#7684\>\<#5404\>\<#79CD\>\<#6807\>\<#7B7E\>>>|<row|<cell|downloadFileFromWP>|<cell|\<#4ECE\>\<#7F51\>\<#76D8\>\<#4E0A\>\<#4E0B\>\<#8F7D\>\<#6587\>\<#4EF6\>>>|<row|<cell|getWPParentDirectory>|<cell|\<#5F97\>\<#5230\>\<#7F51\>\<#76D8\>\<#7279\>\<#5B9A\>\<#76EE\>\<#5F55\>\<#7684\>\<#7236\>\<#76EE\>\<#5F55\>>>|<row|<cell|getWPFileList>|<cell|\<#5F97\>\<#5230\>\<#7279\>\<#5B9A\>\<#7F51\>\<#76D8\>\<#8DEF\>\<#5F84\>\<#4E0B\>\<#7684\>\<#6587\>\<#4EF6\>>>|<row|<cell|getWPFile>|<cell|\<#5F97\>\<#5230\>\<#7F51\>\<#76D8\>\<#7684\>\<#6587\>\<#4EF6\>>>|<row|<cell|changeMode>|<cell|\<#66F4\>\<#6539\>\<#5F53\>\<#524D\>\<#6A21\>\<#5F0F\>>>|<row|<cell|getCurMode>|<cell|\<#5F97\>\<#5230\>\<#5F53\>\<#524D\>\<#6A21\>\<#5F0F\>>>>>>

    \;
  </big-table|>

  <section|ProcessManager>

  \;

  \<#8BE5\>\<#7C7B\>\<#8D1F\>\<#8D23\>\<#5904\>\<#7406\>\<#6240\>\<#6709\>\<#6765\>\<#81EA\>FTPAdapter\<#7684\>\<#4E0A\>\<#4F20\>\<#6216\>\<#4E0B\>\<#8F7D\>\<#7684\>\<#4EFB\>\<#52A1\>\<#8BF7\>\<#6C42\>\<#3002\>\<#9ED8\>\<#8BA4\>\<#540C\>\<#4E00\>\<#65F6\>\<#523B\>\<#53EA\>\<#80FD\>\<#6709\>5\<#4E2A\>\<#4EFB\>\<#52A1\>\<#5728\>\<#4E00\>\<#8D77\>\<#5DE5\>\<#4F5C\>\<#3002\>\<#5B83\>\<#8D1F\>\<#8D23\>\<#7BA1\>\<#7406\>\<#5404\>\<#4E2A\>\<#7EBF\>\<#7A0B\>\<#7684\>\<#8D44\>\<#6E90\>\<#5360\>\<#7528\>\<#548C\>\<#91CA\>\<#653E\>\<#FF0C\>\<#63A7\>\<#5236\>\<#5B83\>\<#4EEC\>\<#662F\>\<#5426\>\<#9700\>\<#8981\>\<#505C\>\<#6B62\>\<#FF0C\>\<#4EE5\>\<#53CA\>\<#8BE2\>\<#95EE\>\<#8FDB\>\<#5EA6\>\<#3002\>

  <subsection|\<#6210\>\<#5458\>\<#53D8\>\<#91CF\>>

  <big-table|<block|<tformat|<table|<row|<cell|\<#53D8\>\<#91CF\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|userName>|<cell|\<#7528\>\<#6237\>\<#540D\>\<#5B57\>>>|<row|<cell|password>|<cell|\<#7528\>\<#6237\>\<#5BC6\>\<#7801\>>>|<row|<cell|CONNUM>|<cell|\<#5BB9\>\<#8BB8\>\<#6700\>\<#5927\>\<#7EBF\>\<#7A0B\>\<#6570\>>>|<row|<cell|server>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#5730\>\<#5740\>>>|<row|<cell|busyNum>|<cell|\<#6B63\>\<#5728\>\<#4F7F\>\<#7528\>\<#7684\>\<#7EBF\>\<#7A0B\>\<#6570\>>>|<row|<cell|vacantNum>|<cell|\<#7A7A\>\<#95F2\>\<#7EBF\>\<#7A0B\>\<#6570\>>>|<row|<cell|busyCon>|<cell|\<#6B63\>\<#5728\>\<#4F7F\>\<#7528\>\<#7684\>\<#7EBF\>\<#7A0B\>\<#5E8F\>\<#53F7\>\<#5217\>\<#8868\>>>|<row|<cell|vacantCon>|<cell|\<#7A7A\>\<#95F2\>\<#7EBF\>\<#7A0B\>\<#5E8F\>\<#53F7\>\<#5217\>\<#8868\>>>|<row|<cell|threads>|<cell|\<#7EBF\>\<#7A0B\>\<#5217\>\<#8868\>>>>>>|>

  <subsection|\<#6210\>\<#5458\>\<#51FD\>\<#6570\>>

  <big-table|<block|<tformat|<table|<row|<cell|\<#51FD\>\<#6570\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|ProcessManager>|<cell|\<#4EFB\>\<#52A1\>\<#7BA1\>\<#7406\>\<#5668\>\<#6784\>\<#9020\>\<#51FD\>\<#6570\>>>|<row|<cell|logout>|<cell|\<#7528\>\<#6237\>\<#767B\>\<#51FA\>>>|<row|<cell|free>|<cell|\<#91CA\>\<#653E\>\<#5DF2\>\<#5B8C\>\<#6210\>\<#7684\>\<#4EFB\>\<#52A1\>>>|<row|<cell|vacant>|<cell|\<#68C0\>\<#67E5\>\<#662F\>\<#5426\>\<#6709\>\<#53EF\>\<#7528\>\<#8D44\>\<#6E90\>>>|<row|<cell|addProcess>|<cell|\<#65B0\>\<#589E\>\<#4EFB\>\<#52A1\>>>|<row|<cell|cutProcess>|<cell|\<#4E2D\>\<#65AD\>\<#4EFB\>\<#52A1\>>>|<row|<cell|getAccomplishRate>|<cell|\<#5F97\>\<#5230\>\<#4EFB\>\<#52A1\>\<#5B8C\>\<#6210\>\<#767E\>\<#5206\>\<#6BD4\>>>>>>|>

  <section|ProcessThread>

  \;

  \<#8BE5\>\<#7C7B\>\<#8D1F\>\<#8D23\>\<#5177\>\<#4F53\>\<#6267\>\<#884C\>\<#4E00\>\<#4E2A\>\<#4EFB\>\<#52A1\>\<#3002\>\<#5B83\>\<#5148\>\<#8BA1\>\<#7B97\>\<#4E86\>\<#4EFB\>\<#52A1\>\<#6D89\>\<#53CA\>\<#5230\>\<#7684\>\<#6240\>\<#6709\>\<#6587\>\<#4EF6\>\<#7684\>\<#603B\>\<#5927\>\<#5C0F\>\<#FF0C\>\<#5F97\>\<#51FA\>\<#5F53\>\<#524D\>\<#5DF2\>\<#5B8C\>\<#6210\>\<#7684\>\<#767E\>\<#5206\>\<#6BD4\>\<#FF0C\>\<#7136\>\<#540E\>\<#518D\>\<#5BF9\>\<#6BCF\>\<#4E2A\>\<#6587\>\<#4EF6\>\<#5206\>\<#522B\>\<#64CD\>\<#4F5C\>\<#FF0C\>\<#5E76\>\<#53CA\>\<#65F6\>\<#66F4\>\<#65B0\>\<#767E\>\<#5206\>\<#6BD4\>\<#3002\>

  <subsection|\<#6210\>\<#5458\>\<#53D8\>\<#91CF\>>

  <big-table|<block|<tformat|<table|<row|<cell|\<#53D8\>\<#91CF\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|DOWNLOAD>|<cell|\<#4E0B\>\<#8F7D\>\<#4EE3\>\<#53F7\>>>|<row|<cell|UPLOAD>|<cell|\<#4E0A\>\<#4F20\>\<#4EE3\>\<#53F7\>>>|<row|<cell|LENGTH>|<cell|\<#5355\>\<#6B21\>\<#4F20\>\<#8F93\>\<#7684\>\<#957F\>\<#5EA6\>>>|<row|<cell|connection>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#8FDE\>\<#63A5\>\<#5668\>>>|<row|<cell|serverFileName>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#6587\>\<#4EF6\>\<#8DEF\>\<#5F84\>>>|<row|<cell|localFileName>|<cell|\<#672C\>\<#5730\>\<#6587\>\<#4EF6\>\<#8DEF\>\<#5F84\>>>|<row|<cell|accomplish>|<cell|\<#5DF2\>\<#5B8C\>\<#6210\>\<#7684\>\<#5927\>\<#5C0F\>>>|<row|<cell|total>|<cell|\<#603B\>\<#5927\>\<#5C0F\>>>|<row|<cell|cut>|<cell|\<#662F\>\<#5426\>\<#9700\>\<#8981\>\<#5207\>\<#65AD\>\<#8BE5\>\<#4EFB\>\<#52A1\>>>|<row|<cell|user>|<cell|\<#7528\>\<#6237\>\<#540D\>\<#5B57\>>>|<row|<cell|pass>|<cell|\<#7528\>\<#6237\>\<#5BC6\>\<#7801\>>>|<row|<cell|path>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#6587\>\<#4EF6\>\<#8DEF\>\<#5F84\>\<#7684\>\<#7236\>\<#76EE\>\<#5F55\>>>|<row|<cell|server>|<cell|\<#670D\>\<#52A1\>\<#5668\>\<#540D\>\<#5B57\>>>|<row|<cell|manager>|<cell|\<#4EFB\>\<#52A1\>\<#7BA1\>\<#7406\>\<#5668\>>>|<row|<cell|type>|<cell|\<#4E0A\>\<#4F20\>\<#8FD8\>\<#662F\>\<#4E0B\>\<#8F7D\>\<#7684\>\<#6807\>\<#5FD7\>>>|<row|<cell|num>|<cell|\<#6240\>\<#7528\>\<#7684\>\<#7EBF\>\<#7A0B\>\<#5E8F\>\<#53F7\>>>|<row|<cell|withWP>|<cell|\<#662F\>\<#5426\>\<#548C\>\<#7F51\>\<#76D8\>\<#540C\>\<#6B65\>>>>>>|>

  <subsection|\<#6210\>\<#5458\>\<#51FD\>\<#6570\>>

  <big-table|<block|<tformat|<table|<row|<cell|\<#51FD\>\<#6570\>\<#540D\>>|<cell|\<#529F\>\<#80FD\>>>|<row|<cell|ProcessThread>|<cell|\<#65B0\>\<#5EFA\>\<#4EFB\>\<#52A1\>\<#7EBF\>\<#7A0B\>>>|<row|<cell|run>|<cell|\<#7EBF\>\<#7A0B\>\<#7684\>\<#6267\>\<#884C\>>>|<row|<cell|startProgress>|<cell|\<#5F00\>\<#59CB\>\<#590D\>\<#5236\>\<#7684\>\<#8FC7\>\<#7A0B\>>>|<row|<cell|calSize>|<cell|\<#8BA1\>\<#7B97\>\<#4EFB\>\<#52A1\>\<#5217\>\<#8868\>\<#7684\>\<#603B\>\<#5927\>\<#5C0F\>>>|<row|<cell|checkSize>|<cell|\<#8BA1\>\<#7B97\>\<#6587\>\<#4EF6\>\<#5939\>\<#603B\>\<#5927\>\<#5C0F\>>>|<row|<cell|copyDirectory>|<cell|\<#590D\>\<#5236\>\<#6587\>\<#4EF6\>\<#5939\>>>|<row|<cell|tryCopyFile>|<cell|\<#590D\>\<#5236\>\<#6587\>\<#4EF6\>>>>>>|>
</body>

<initial|<\collection>
</collection>>

<\references>
  <\collection>
    <associate|auto-1|<tuple|1|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-10|<tuple|3.2|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-11|<tuple|4|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-12|<tuple|4|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-13|<tuple|4.1|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-14|<tuple|5|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-15|<tuple|4.2|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-16|<tuple|6|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-2|<tuple|2|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-3|<tuple|2.1|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-4|<tuple|1|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-5|<tuple|2.2|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-6|<tuple|2|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-7|<tuple|3|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-8|<tuple|3.1|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
    <associate|auto-9|<tuple|3|?|../.TeXmacs/texts/scratch/no_name_2.tm>>
  </collection>
</references>

<\auxiliary>
  <\collection>
    <\associate|table>
      <tuple|normal||<pageref|auto-4>>
    </associate>
    <\associate|toc>
      <vspace*|1fn><with|font-series|<quote|bold>|math-font-series|<quote|bold>|1<space|2spc>\<#5305\>\<#7684\>\<#5B9A\>\<#4F4D\>>
      <datoms|<macro|x|<repeat|<arg|x>|<with|font-series|medium|<with|font-size|1|<space|0.2fn>.<space|0.2fn>>>>>|<htab|5mm>>
      <no-break><pageref|auto-1><vspace|0.5fn>

      <vspace*|1fn><with|font-series|<quote|bold>|math-font-series|<quote|bold>|2<space|2spc>FTPAdapter>
      <datoms|<macro|x|<repeat|<arg|x>|<with|font-series|medium|<with|font-size|1|<space|0.2fn>.<space|0.2fn>>>>>|<htab|5mm>>
      <no-break><pageref|auto-2><vspace|0.5fn>

      <with|par-left|<quote|1tab>|2.1<space|2spc>\<#6210\>\<#5458\>\<#53D8\>\<#91CF\>
      <datoms|<macro|x|<repeat|<arg|x>|<with|font-series|medium|<with|font-size|1|<space|0.2fn>.<space|0.2fn>>>>>|<htab|5mm>>
      <no-break><pageref|auto-3>>
    </associate>
  </collection>
</auxiliary>