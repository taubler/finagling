namespace * com.taubler.greet.thrift

enum Salutation {
    Mr,
    Mrs,
    Ms,
    Dr
}

struct FullName {
  1: string firstName,
  2: string lastName,
  3: optional string suffix,
  4: optional Salutation salutation
}

service GreetingService {
  string beGreeted(1: FullName name)
  string beUngreeted(1: FullName name, 2: string newLocation)
}