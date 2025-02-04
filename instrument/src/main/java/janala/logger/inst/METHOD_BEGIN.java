package janala.logger.inst;

public class METHOD_BEGIN extends Instruction implements MemberRef {
  public final String owner;
  public final String name;
  public final String desc;
  private final Object obj;

  public METHOD_BEGIN(String owner, String name, String desc) {
    super(-1, -1);
    this.owner = owner;
    this.name = name;
    this.desc = desc;
    this.obj = null;
  }

  public METHOD_BEGIN(String owner, String name, String desc, Object obj) {
    super(-1, -1);
    this.owner = owner;
    this.name = name;
    this.desc = desc;
    this.obj = obj;
  }

  public void visit(IVisitor visitor) {
    visitor.visitMETHOD_BEGIN(this);
  }

  public Object getObject() {
    return obj;
  }

  @Override
  public String toString() {
    return "METHOD_BEGIN"
            + " owner="
            + owner
            + " name="
            + name
            + " desc="
            + desc;
  }


  @Override
  public String getOwner() {
    return owner;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDesc() {
    return desc;
  }
}
