/* 
 * client.c
 *
 * sklar modified from http://dada.perl.it/shootout/echo_allsrc.html
 *
 * on sunOS, compile like this: gcc client.c -o client -lsocket 
 * on linux, compile like this: gcc client.c -o client
 *
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <netinet/in.h>


/* sklar: need to define socklen_t for sunOS; not needed for linux */
/* typedef int socklen_t; */

#define TERM '\n'




/*--------------------------------------------------------------------
  sysabort()

  system error occured; reports error and exits program
  ------------------------------------------------------------------*/
void sysabort ( char *m ) { 
    perror( m ); 
    exit( 1 );
} /* end of sysabort() */


/*--------------------------------------------------------------------
  client_sock()

  creates a socket and then attempts to make a connection to that 
  socket
  ------------------------------------------------------------------*/
int client_sock ( int port ) {

    int ss, optval = 1;
    struct sockaddr_in sin;

    /* (1) create an endpoint for communication */
    if (( ss = socket( PF_INET, SOCK_STREAM, 0 )) == -1) {
	sysabort( "client/socket" );
    }

    /* (1a) set socket options */
    if ( setsockopt( ss,
		     SOL_SOCKET, 
		     SO_REUSEADDR, /* basically allows socket to bind */
		     /*(const char *)&optval, sizeof(optval)) == -1 ) {*/
		     &optval, sizeof(optval)) == -1 ) {
	sysabort( "client/setsockopt" );
    }
    memset( &sin, 0, sizeof( sin ));
    sin.sin_family = AF_INET;
    sin.sin_addr.s_addr = htonl( INADDR_LOOPBACK );
    sin.sin_port = port;

    /* (2) make a connection to the server socket */
    if ( connect( ss,(struct sockaddr *)&sin,(socklen_t)sizeof(sin) ) == -1 ) {
	sysabort( "client/connect" );
    }

    /* return the socket descriptor */
    return( ss );

} /* end of client_sock() */


/*--------------------------------------------------------------------
  echo_client()
  ------------------------------------------------------------------*/
void echo_client ( int port ) {

    int sock, olen, nwritten, nread, more;
    unsigned char i;
    char ibuf[64], obuf[64], *p;
    
    /* create client socket on port... */
    printf( "client: port=%d\n",port );
    sock = client_sock( port );
    
    /* loop, reading input from client and sending it to server */
    more = 1;
    while ( more ) {
	printf( "enter message to send (q to quit): " );
	p = obuf;
	p = fgets( p,64,stdin );
	p[strlen(p)-1] = '\0';
	if ( p[0] == 'q' ) {
	    more = 0;
	    p[0] = TERM;
	    p[1] = '\0';
	}
	else {
	    printf( "client: writing message [%s]\n",p );
	    i = strlen( p );
	}
	if (( nwritten = write( sock, &i, sizeof( i ) )) == -1 ) {
	    sysabort( "client/write" );
	}
	if (( nwritten = write( sock, p, strlen(p) )) == -1 ) {
	    sysabort( "client/write" );
	}
	if ( more ) {
	    if (( nread = read( sock, &i, sizeof( i ))) < 0 ) {
		sysabort( "client/read" );
	    }
	    fprintf( stdout,"client: i=%d\n",i );
	    fflush( stdout );
	    if (( nread = read( sock, ibuf, i )) < 0 ) {
		sysabort( "client/read" );
	    }
	    ibuf[nread] = '\0';
	    if ( ibuf[0] == TERM ) {
		more = 0;
	    }
	    else {
		fprintf( stdout,"client: read message [%s]\n",ibuf );
		fflush( stdout );
	    }
	}
    } /* end while more */
    
    /* close socket connection */
    close( sock );
    
} /* end of echo_client() */


/*--------------------------------------------------------------------
  main()
  ------------------------------------------------------------------*/
int main( int argc, char *argv[] ) {
    int port;
    if ( argc < 2 ) {
	printf( "usage: client.x <port>\n" );
	exit( 1 );
    }
    sscanf( argv[1],"%d",&port );
    echo_client(  port );
    return( 0 );
} /* end of main() */
