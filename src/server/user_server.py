import sys
from omniORB import CORBA
import CosNaming
from omniORB.CORBA import BOA
from omniORB import PortableServer

# Definición del servicio
class UserServiceImpl:
    def __init__(self):
        self.usuarios = {"Gandhi", "Arturo", "Juan", "Maria", "Pedro", "Jose", "Ana"}

    def validarUsuario(self, idUsuario):
        return idUsuario in self.usuarios


def main():
    try:
        # Inicializa el ORB
        orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)
        poa = orb.resolve_initial_references("RootPOA")
        poa_manager = poa._get_the_POAManager()
        poa_manager.activate()

        # Crea el servicio
        servant = UserServiceImpl()

        # Registra el objeto en el POA
        obj_ref = poa.servant_to_reference(servant)

        # Obtiene el servicio de nombres
        naming_context = orb.resolve_initial_references("NameService")
        naming_context = naming_context._narrow(CosNaming.NamingContext)

        # Crea el nombre del servicio y lo registra
        name = "UsuarioService"
        name_component = [CosNaming.NameComponent(name, "")]
        naming_context.rebind(name_component, obj_ref)

        print(f"{name} registrado en el servicio de nombres.")
        print("Servidor listo y esperando conexiones...")

        # Ejecuta el ORB
        orb.run()
    except Exception as e:
        print(f"Error al configurar el servidor: {e}")


if __name__ == "__main__":
    main()
