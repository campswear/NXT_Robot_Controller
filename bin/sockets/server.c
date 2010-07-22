/* 
 * server.c
 *
 * sklar modified from http://dada.perl.it/shootout/echo_allsrc.html
 *
 * on sunOS, compile like this: gcc server.c -o server -lsocket 
 * on linux, compile like this: gcc server.c -o server
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


/* need to define socklen_t for sunOS; not needed for linux */
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
  server_sock()
  
  creates a socket and then binds an address to that socket, then
  specifies willingness to accept incoming connections via listen
  ------------------------------------------------------------------*/
int server_sock () {

  int ss, optval = 1;
  struct sockaddr_in sin;

  /* (1) create an endpoint for communication */
  if ((ss = socket(PF_INET, SOCK_STREAM, 0)) == -1) {
    sysabort( "server/socket" );
  }

  /* (1a) set socket options */
  if ( setsockopt( ss,
		   SOL_SOCKET, /* to manipulate options at the socket level */
		   SO_REUSEADDR, /* basically allows socket to bind */
		   (const char *)&optval, sizeof(optval)) == -1 ) {
    sysabort( "server/setsockopt" );
  }
  memset( &sin, 0, sizeof( sin ));
  sin.sin_family = AF_INET;
  sin.sin_addr.s_addr = htonl( INADDR_LOOPBACK );
  sin.sin_port = 0;

  /* (2) bind the socket */
  if ( bind( ss,(struct sockaddr *)&sin,(socklen_t)sizeof(sin) ) == -1 ) {
    sysabort( "server/bind" );
  }

  /* (3) listen for connections */
  listen( ss,2 );

  /* return the socket descriptor */
  return( ss );

} /* end of server_sock() */



/*--------------------------------------------------------------------
  get_port()

  ------------------------------------------------------------------*/
int get_port ( int sock ) {
  struct sockaddr_in sin;
  socklen_t slen = sizeof( sin );
  /* get the name of specified socket "sock" and store it in "sin" */
  if ( getsockname(sock, (struct sockaddr *)&sin, &slen) == -1 ) {
    sysabort( "getsockname" );
  }
  return( sin.sin_port );
} /* end of get_port() */



/*--------------------------------------------------------------------
  echo_server()
  ------------------------------------------------------------------*/
void echo_server () {
  int ssock, csock, more, nread, nwritten, status;
  unsigned char i;
  pid_t pid;
  char ibuf[64], obuf[64], *p;
  struct sockaddr_in sin;
  socklen_t slen = sizeof( sin );
  int hostlen=1024;
  char *host=(char *)malloc( hostlen*sizeof( char ));

  /* creates server socket */
  ssock = server_sock();
  gethostname( host,hostlen );
  printf( "host: [%s]\n",host );
  /*printf( "port: %d\n", ntohs( get_port( ssock )));*/
  printf( "port --> for non-linux=[%d] or for linux=[%d]\n", 
	  ntohs( get_port( ssock )),get_port( ssock ));

  /* accept returns a socket descriptor for the accepted socket */
  if (( csock = accept( ssock, 
			(struct sockaddr *)&sin, &slen )) == -1 ) {
    sysabort( "server/accept" );
  }
  fprintf( stdout,"server: socket created/listen/accepted\n" );
  fflush( stdout );

  /* loop while reading on the socket... */
  more = 1;
  while ( more ) {
    if (( nread = read( csock, &i, sizeof(i))) < 0 ) {
      sysabort( "server/read" );
    }
    else {
      fprintf( stdout,"server: i=%d\n",i );
      fflush( stdout );
      if (( nread = read( csock, ibuf, i )) < 0 ) {
	sysabort( "server/read" );
      }
      ibuf[nread] = '\0';
      if ( ibuf[0] == TERM ) {
	more = 0;
      }
      else {
	fprintf( stdout,"server: read message [%s]\n",ibuf );
	fflush( stdout );
	/* now do output... */
	strcpy( obuf,"okay" );
	p = obuf;
	i = strlen( obuf );
	fprintf( stdout,"server: writing message [%s]\n",p );
	fflush( stdout );
	if (( nwritten = write( csock, &i, sizeof(i) )) == -1 ) {
	  sysabort( "server/write" );
	}
	if (( nwritten = write( csock, p, strlen(p) )) == -1 ) {
	  sysabort( "server/write" );
	}
      }
    }
  } /* end while more */

  /* close socket connection */
  close( csock );

} /* end of echo_server() */



/*--------------------------------------------------------------------
  main()
  ------------------------------------------------------------------*/
int main( int argc, char *argv[] ) {
  echo_server( 1 );
  return( 0 );
} /* end of main() */
