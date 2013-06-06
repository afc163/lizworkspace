#!/bin/bash

REMOTE_LOG_FILE="ftp://tc-pv-log11.tc.baidu.com//home/pageview/tmp/mp3_noresult.log"
RAW_LOG_FILE="./raw/mp3_noresult.log"
RAW_WORD_FILE="./raw/mp3_noresult.word"
CAST_WORD_FILE="./cast/mp3_sim.cast"

SORT_TMP_PATH="/home/mp3/mp3sim"
WORD_NUM=10000
tmp_file1="./raw/t1"
tmp_file2="./raw/t2"

cd /home/mp3/mp3sim
MAIL_LIST=$(head -n 1 mail.conf)

#######################################################
# 从远程服务器获取无结果日志文件    因为现在实现的代码，以后还可能改变，这样子可能就又要重新考虑以前已经实现的代码，为了新的功能而重新做，所以我觉得还是先说的详细点，很细节的（最好是最终要达到的目的）都可以，不要怕我接受不了，这样我也大概知道还有多少东西要事先考虑以作为扩展，当然，具体的实现细节就不必那么详细了，呵呵，我可以自己去想，不懂的地方我在请教你。然后你说要
retry=5
while test ${retry} -gt 0
do
   wget -q ${REMOTE_LOG_FILE} -O ${RAW_LOG_FILE} -o /dev/null
   ret=$?
   if test ${ret} -eq 0
   then
      echo "get ${REMOTE_LOG_FILE} success"
      break
   fi
   retry=$[ ${retry} - 1 ]
   sleep 10
done

if test ${retry} -eq 0
then
     echo "can't get ${REMOTE_LOG_FILE}"
     echo "can't get ${REMOTE_LOG_FILE}" | mail -s "Waring:create mp3 similar word list failed" ${MAIL_LIST}
    exit 1
fi

# 得到top 1000词表
awk '{if(i=match($0,/ \[.*\] rs=/))print substr($0,RSTART+2,RLENGTH-7)}' ${RAW_LOG_FILE} > ${tmp_file1}
sort ${tmp_file1} -T ${SORT_TMP_PATH} | uniq -c | sort -k1 -n -r -T ${SORT_TMP_PATH} > ${tmp_file2}
head -n ${WORD_NUM} ${tmp_file2} | cut -f 2 > ${RAW_WORD_FILE}

rm -f ${tmp_file1}
rm -f ${tmp_file2}

# 生成词表长度是否为0
if test ! -s ${RAW_WORD_FILE}
then
   echo "get top ${WORD_NUM} list failed"
   echo "get top ${WORD_NUM} list failed" | mail -s "Waring:create mp3 similar word list failed" ${MAIL_LIST}
   exit 1
fi

##########################################################
# 删除生成词表中的:,|,=等三个特殊字符，因为ＲＳ不支持
mv ${RAW_WORD_FILE} ${RAW_WORD_FILE}.tmp
grep -v "[:|=]" ${RAW_WORD_FILE}.tmp > ${RAW_WORD_FILE}
ret=$?
if test ${ret} -ne 0
then
    echo "grep return failed"
    mv ${RAW_WORD_FILE}.tmp ${RAW_WORD_FILE}
fi
rm -f ${RAW_WORD_FILE}.tmp

date

##########################################################
# 建立相似词表
mv -f ${CAST_WORD_FILE} ${CAST_WORD_FILE}.back

./bin/simgen ${RAW_WORD_FILE} ${CAST_WORD_FILE}
ret=$?
if test ${ret} -ne 0
then
   echo "simgen return failed"
   echo "simgen return faield" | mail -s "Waring:create mp3 similar word list failed" ${MAIL_LIST}
   # 恢复备份
   mv ${CAST_WORD_FILE}.back ${CAST_WORD_FILE}
   exit 1
fi
echo "create mp3 similiar word list successfully"
echo "create mp3 similiar word list successfully" | mail -s "create mp3 similiar word list successfully" ${MAIL_LIST}

exit 0


                                                                             


