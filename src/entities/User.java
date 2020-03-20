package entities;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import view.ScreenLogin;
import view.ScreenLogin.Login;

public class User {

    private String cpf;
    private String password;
    private String name;
    private Boolean userValidated;

    public User() {
    }

    public User(String cpf, String password, String name, Boolean userValidated) {
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.userValidated = userValidated;
    }

    Scanner sc = new Scanner(System.in);

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUserValidated() {
        return userValidated;
    }

    public void setUserValidated(Boolean userValidated) {
        this.userValidated = userValidated;
    }

    /**
     * It adds an user to the file.
     */
    public void addUser() {
        System.out.print("Usuário, digite o seu cpf: ");
        String cpf = sc.next();
        System.out.print("Digite sua senha: ");
        String password = sc.next();
        sc.nextLine();
        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();
        Boolean userValidated = Boolean.FALSE;
        User user = new User(cpf, password, name, userValidated);
        Database db = new Database();
        List<User> users = db.recoverUsers();
        users.add(user);
        db.recordUsers(users);
        System.out.println("Operação realizada com sucesso.\n");
    }//End of method addUser.

    /**
     * It removes an user from the file.
     */
    public void removeUser() {
        Database db = new Database();
        List<User> users = db.recoverUsers();
        for (User user : users) {
            System.out.println("Posição: " + users.indexOf(user) + ", nome: " + user.getName() + ", cpf: " + user.getCpf() + ".");
        }
        System.out.print("\nQual a posição do usuário que deseja remover? ");
        int position = sc.nextInt();
        users.remove(position);
        db.recordUsers(users);
        System.out.println("Operação realizada com sucesso.\n");
    }//End of method removeUser.

    /**
     * It reads users in the file and adds one if there isn't anyone there.
     */
    public void readUsers() {
        User user = new User();
        Database db = new Database();
        List<User> users = db.recoverUsers();
        if (users.size() == 0) {
            user.addUser();
        }
    }//End of method readUsers.

    /**
     * It searchs all users in the file and shows their data.
     */
    public void searchUsers() {
        System.out.println();
        Database db = new Database();
        List<User> users = db.recoverUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }//End of method searchUsers.

    /**
     * It validates the user, welcomes him, and alert him for foot and mouth
     * vaccination in May and November.
     *
     * @return
     */
    public boolean validateUser() {
        ScreenLogin.Login sll = new ScreenLogin().new Login();
        sc.nextLine();
        String typedCpf = sll.getCpf();
        String typedPassword = sll.getPassword();
        Database db = new Database();
        List<User> users = db.recoverUsers();
        for (User user : users) {
            if (user.getCpf().equalsIgnoreCase(typedCpf) && user.getPassword().equalsIgnoreCase(typedPassword)) {
                user = new User(user.getCpf(), user.getPassword(), user.getName(), user.getUserValidated());
                userValidated = !user.getUserValidated();
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH) + 1;
                int ano = c.get(Calendar.YEAR);
                if (hora >= 6 && hora < 12) {
                    System.out.println("\nBom dia " + user.getName() + ", seja bem vindo! Hoje é dia " + dia + "/" + mes + "/" + ano + ".\n");
                } else if (hora >= 12 && hora < 18) {
                    System.out.println("\nBoa tarde " + user.getName() + ", seja bem vindo! Hoje é dia " + dia + "/" + mes + "/" + ano + ".\n");
                } else {
                    System.out.println("\nBoa noite " + user.getName() + ", seja bem vindo! Hoje é dia " + dia + "/" + mes + "/" + ano + ".\n");
                }
                if (mes == 5 || mes == 11) {
                    System.out.println("Não esquecer a vacina contra febre aftosa este mês!\n");
                }
            } else {
                userValidated = user.getUserValidated();
            }
        }
        return userValidated;
    }//End of method validatedUser.

    @Override
    public String toString() {
        return "Usuário: " + getName() + ", cpf: " + getCpf() + ".";
    }
}
