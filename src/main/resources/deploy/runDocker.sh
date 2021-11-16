#!/usr/bin
#!/bin/bash
# 注意：环境变量要放在docker镜像名前面！
# LANG=en_US.utf8 指定终端输出编码为UTF8，支持中文
# 调试使用 -i 运行使用 -d
# 指定挂载卷
docker run \
    --network host \
    -e ENV \
    -e APOLLO_META \
    -e LANG=en_US.utf8 \
    -e APP_ID=cdn-log \
    -v /usr/hdp/3.1.0.0-78/hadoop/:/usr/hdp/3.1.0.0-78/hadoop/:ro \
	  -v /etc/hadoop/3.1.0.0-78/0/:/etc/hadoop/3.1.0.0-78/0/:ro \
    -v /etc/hosts:/etc/hosts:ro  \
    -v /etc/cdnlog:/etc/cdnlog:ro \
    -v /etc/security/keytabs/cdnlog.keytab:/etc/security/keytabs/cdnlog.keytab:ro \
    -v /etc/krb5.conf:/etc/krb5.conf:ro \
    -v /var/kerberos/krb5kdc/kdc.conf:/var/kerberos/krb5kdc/kdc.conf:ro \
    -d \
    harbor.ctyuncdn.cn/cdn-log/rest/cdn-log-config-rest:${TAG}
