PORT_HTTP=80;
PORT_APP=8080;

sudo iptables -t nat -A PREROUTING -p tcp --dport ${PORT_HTTP} -j REDIRECT --to-port ${PORT_APP}