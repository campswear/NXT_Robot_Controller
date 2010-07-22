package sockets;
//import sockets.Client;
//import sockets.Server;
public class Foo {
	
/*
	//Foo()
	//{	};
	
    public void readconfig(const char *, const char *);
    ident write_ident();
    public void read_ident(ident);
    public void print_ident();

    void set_session_id(int);
    int get_session_id();

    void set_owner_id(int);
    int get_owner_id();

    int get_type_id();

    void set_x(int);
    int get_x();

    void set_y(int);
    int get_y();

    void set_theta(int);
    int get_theta();

    const char *get_name();
*/

//private:
    //struct ident tag;
    //char **provides;
//};
	//private static Ident tag = new Ident();
	private static String[] provides;
    int session_id;
    int owner_id=999;
    int type_id;
    int x;
    int y;
    int theta;
    int num_of_provides=0;
    String name="";
	public static int xxx=-111;
    
    public void Foo()
    {
        provides = new String [10];
        num_of_provides = 0;
        owner_id = -1;
        session_id = -1;
        type_id = -1;
        x = 0;
        y = 0;
        theta = 90;
        System.out.print("constructor called:theta"+theta);
    }
/*
    public void readconfig(const char *configpath, const char *Fooid)
    {
        ConfigFile cf = new ConfigFile();
        bool done = false;

        cf->Load(configpath);
        int count = cf->GetSectionCount();

        for(int i = 1; i < count && !done; ++i)
        {
            const char *cfg_name = cf->ReadString(i, "name", "failed");

            strncpy(name, cfg_name, FIELD_SIZE);

            System.out.println( "Section: "  ) name;

            if(strncmp(name, Fooid, strlen(Fooid)) == 0)
            {

                num_of_provides = cf->GetTupleCount(i, "provides");
                provides = new char*[num_of_provides];

                for(int j = 0; j < num_of_provides; ++j)
                {
                    const char *cfg_provides = cf->ReadTupleString(i, "provides", j, "failed");

                    provides[j] = new char[FIELD_SIZE];

                    strncpy(provides[j], cfg_provides, FIELD_SIZE);

                    System.out.println( "  Provides -> "  ) provides[j];
                }

                done = true;

            }

        }

        delete cf;
    }

    public Ident write_ident()
    {
      return tag;
    }

    public void read_ident(Ident info){
      tag = info;
    }
*/
    public void print_ident(){
        System.out.println( "Owner ID: "  + this.owner_id);
        System.out.println( "Session ID: "  + session_id);
        System.out.println( "Type ID: "  + type_id);
        System.out.println( "X-Coordinate: "  + x);
        System.out.println( "Y-Coordinate: "  + y);
        System.out.println( "Theta: "  + this.theta);
        System.out.println( "XXX "  + xxx);
    }

    public void set_session_id(int id){
      session_id = id;
    }

    public int get_session_id(){
      return session_id;
    }

    public void set_owner_id(int id){
      owner_id = id;
    }

    public int get_owner_id(){
      return owner_id;
    }

    public int get_type_id(){
      return type_id;
    }

    public void set_x(int x_coord){
      x = x_coord;
    }

    public int get_x(){
      return x;
    }

    public void set_y(int y_coord){
      y = y_coord;
    }

    public int get_y(){
      return y;
    }

    public void set_theta(int theta_pos){
      theta = theta_pos;
    }

    public int get_theta(){
      return theta;
    }

    public final String get_name(){
      return name;
    }
/*
    ~Foo()
    {
      if(provides != NULL){
        for( int i = 0 ; i < num_of_provides ; i++ )
            delete [] provides[i];

        delete [] provides;
      }
      
    }
    */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

}
